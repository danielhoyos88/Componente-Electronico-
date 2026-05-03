import tkinter as tk
from tkinter import ttk, messagebox
import api_service as api

# ───────────────────────── INSERT ACTIVE ─────────────────────────
class FormInsertActive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Insertar Componente Activo")
        self.resizable(False, False)
        self.grab_set()

        fields = ["ID", "Marca", "Encapsulado", "Voltaje", "Corriente", "Factor Ganancia"]
        self.entries = {}
        for i, f in enumerate(fields):
            tk.Label(self, text=f + ":").grid(row=i, column=0, sticky="e", padx=10, pady=4)
            e = tk.Entry(self, width=28)
            e.grid(row=i, column=1, padx=10, pady=4)
            self.entries[f] = e

        tk.Label(self, text="Pines (separados por coma):").grid(row=len(fields), column=0, sticky="e", padx=10, pady=4)
        self.txt_pins = tk.Entry(self, width=28)
        self.txt_pins.insert(0, "Base,Colector,Emisor")
        self.txt_pins.grid(row=len(fields), column=1, padx=10, pady=4)

        tk.Label(self, text="ID Fabricante:").grid(row=len(fields)+1, column=0, sticky="e", padx=10, pady=4)
        self.txt_fab = tk.Entry(self, width=28)
        self.txt_fab.insert(0, "")
        self.txt_fab.grid(row=len(fields)+1, column=1, padx=10, pady=4)
        tk.Label(self, text="(Opcional)", fg="gray").grid(row=len(fields)+1, column=2, sticky="w")

        tk.Button(self, text="Guardar", bg="#1e50a0", fg="white",
                  command=self._save).grid(row=len(fields)+2, column=1, pady=10, sticky="e", padx=10)

    def _save(self):
        try:
            e = self.entries
            pins = [p.strip() for p in self.txt_pins.get().split(",")]
            fab_id = self.txt_fab.get().strip()
            fab = api.get_fabricante(int(fab_id)) if fab_id else None
            data = {
                "id": int(e["ID"].get()),
                "brand": e["Marca"].get().strip(),
                "packageType": e["Encapsulado"].get().strip(),
                "voltage": float(e["Voltaje"].get()),
                "current": float(e["Corriente"].get()),
                "gainFactor": float(e["Factor Ganancia"].get()),
                "pinNames": pins,
                "fabricante": fab
            }
            api.add_active(data)
            messagebox.showinfo("Éxito", "Componente activo insertado correctamente.")
            self.destroy()
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── SEARCH ACTIVE ─────────────────────────
class FormSearchActive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Consultar Componente Activo")
        self.resizable(False, False)
        self.grab_set()

        tk.Label(self, text="ID:").grid(row=0, column=0, padx=10, pady=6, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=6)

        tk.Label(self, text="Marca:").grid(row=1, column=0, padx=10, pady=6, sticky="e")
        self.txt_brand = tk.Entry(self, width=20)
        self.txt_brand.grid(row=1, column=1, padx=10, pady=6)

        tk.Label(self, text="(Use ID o Marca)", fg="gray").grid(row=2, column=0, columnspan=2)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=2, column=2, padx=10)

        self.result = tk.Text(self, width=50, height=12, state="disabled", bg="#e8e8e8", fg="black", font=("Courier", 10))
        self.result.grid(row=3, column=0, columnspan=3, padx=10, pady=10)

    def _search(self):
        try:
            id_val = self.txt_id.get().strip()
            brand_val = self.txt_brand.get().strip()
            c = api.search_active(
                id=int(id_val) if id_val else None,
                brand=brand_val if brand_val else None
            )
            self.result.config(state="normal")
            self.result.delete("1.0", tk.END)
            if not c:
                self.result.insert(tk.END, "Componente no encontrado.")
            else:
                self.result.insert(tk.END,
                    f"ID:               {c['id']}\n"
                    f"Marca:            {c['brand']}\n"
                    f"Encapsulado:      {c['packageType']}\n"
                    f"Voltaje:          {c['voltage']} V\n"
                    f"Corriente:        {c['current']} A\n"
                    f"Num. Pines:       {c['pinCount']}\n"
                    f"Factor Ganancia:  {c['gainFactor']}\n"
                    f"Pines:            {', '.join(c['pinNames'])}\n"
                    f"Fecha Registro:   {c.get('registrationDate','N/A')}")
            self.result.config(state="disabled")
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── UPDATE ACTIVE ─────────────────────────
class FormUpdateActive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Actualizar Componente Activo")
        self.resizable(False, False)
        self.grab_set()
        self._current = None

        tk.Label(self, text="ID a actualizar:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=10)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        fields = ["Marca", "Encapsulado", "Voltaje", "Corriente", "Factor Ganancia"]
        self.entries = {}
        self.edit_frame = tk.Frame(self)

        for i, f in enumerate(fields):
            tk.Label(self.edit_frame, text=f + ":").grid(row=i, column=0, sticky="e", padx=10, pady=4)
            e = tk.Entry(self.edit_frame, width=28)
            e.grid(row=i, column=1, padx=10, pady=4)
            self.entries[f] = e

        tk.Label(self.edit_frame, text="Pines (coma):").grid(row=len(fields), column=0, sticky="e", padx=10, pady=4)
        self.txt_pins = tk.Entry(self.edit_frame, width=28)
        self.txt_pins.grid(row=len(fields), column=1, padx=10, pady=4)

        tk.Button(self.edit_frame, text="Guardar cambios", bg="#1e50a0", fg="white",
                  command=self._save).grid(row=len(fields)+1, column=1, pady=10, sticky="e", padx=10)

    def _search(self):
        try:
            self._current = api.get_active(int(self.txt_id.get()))
            if not self._current:
                messagebox.showwarning("No encontrado", "Componente no encontrado.")
                self.edit_frame.grid_remove()
                return
            c = self._current
            self.entries["Marca"].delete(0, tk.END); self.entries["Marca"].insert(0, c["brand"])
            self.entries["Encapsulado"].delete(0, tk.END); self.entries["Encapsulado"].insert(0, c["packageType"])
            self.entries["Voltaje"].delete(0, tk.END); self.entries["Voltaje"].insert(0, c["voltage"])
            self.entries["Corriente"].delete(0, tk.END); self.entries["Corriente"].insert(0, c["current"])
            self.entries["Factor Ganancia"].delete(0, tk.END); self.entries["Factor Ganancia"].insert(0, c["gainFactor"])
            self.txt_pins.delete(0, tk.END); self.txt_pins.insert(0, ",".join(c["pinNames"]))
            self.edit_frame.grid(row=1, column=0, columnspan=3)
        except Exception as ex:
            messagebox.showerror("Error", str(ex))

    def _save(self):
        try:
            e = self.entries
            self._current.update({
                "brand": e["Marca"].get().strip(),
                "packageType": e["Encapsulado"].get().strip(),
                "voltage": float(e["Voltaje"].get()),
                "current": float(e["Corriente"].get()),
                "gainFactor": float(e["Factor Ganancia"].get()),
                "pinNames": [p.strip() for p in self.txt_pins.get().split(",")]
            })
            api.update_active(self._current["id"], self._current)
            messagebox.showinfo("Éxito", "Componente activo actualizado correctamente.")
            self.destroy()
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── DELETE ACTIVE ─────────────────────────
class FormDeleteActive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Eliminar Componente Activo")
        self.resizable(False, False)
        self.grab_set()
        self._found = None

        tk.Label(self, text="ID:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=10)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        self.result = tk.Text(self, width=50, height=10, state="disabled", bg="#e8e8e8", fg="black", font=("Courier", 10))
        self.result.grid(row=1, column=0, columnspan=3, padx=10, pady=5)

        self.btn_delete = tk.Button(self, text="Eliminar", bg="firebrick", fg="white",
                                    width=20, command=self._delete, state="disabled")
        self.btn_delete.grid(row=2, column=0, columnspan=3, pady=10)

    def _search(self):
        try:
            self._found = api.get_active(int(self.txt_id.get()))
            self.result.config(state="normal")
            self.result.delete("1.0", tk.END)
            if not self._found:
                self.result.insert(tk.END, "Componente no encontrado.")
                self.btn_delete.config(state="disabled")
            else:
                c = self._found
                self.result.insert(tk.END,
                    f"ID:               {c['id']}\n"
                    f"Marca:            {c['brand']}\n"
                    f"Encapsulado:      {c['packageType']}\n"
                    f"Voltaje:          {c['voltage']} V\n"
                    f"Corriente:        {c['current']} A\n"
                    f"Factor Ganancia:  {c['gainFactor']}\n"
                    f"Pines:            {', '.join(c['pinNames'])}\n"
                    f"Fecha Registro:   {c.get('registrationDate','N/A')}")
                self.btn_delete.config(state="normal")
            self.result.config(state="disabled")
        except Exception as ex:
            messagebox.showerror("Error", str(ex))

    def _delete(self):
        if not self._found: return
        if messagebox.askyesno("Confirmar", f"¿Eliminar componente ID {self._found['id']} ({self._found['brand']})?"):
            try:
                api.delete_active(self._found["id"])
                messagebox.showinfo("Éxito", "Componente eliminado correctamente.")
                self.destroy()
            except Exception as ex:
                messagebox.showerror("Error", str(ex))


# ───────────────────────── OBSERVER (cliente) ─────────────────────────
class ListObserver:
    """Observer que actualiza la etiqueta de estado al recibir datos del Listar."""
    def __init__(self, label: tk.Label):
        self._label = label

    def on_data_loaded(self, count: int, filtered: bool):
        msg = f"{'Filtro aplicado' if filtered else 'Todos los registros'}: {count} componente(s) encontrado(s)."
        self._label.config(text=msg, fg="#1e50a0")


# ───────────────────────── LIST ACTIVE ─────────────────────────
class FormListActive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Listar Componentes Activos")
        self.geometry("1100x450")
        self.grab_set()

        filter_frame = tk.Frame(self)
        filter_frame.pack(fill="x", padx=10, pady=8)

        tk.Label(filter_frame, text="Marca:").pack(side="left")
        self.txt_brand = tk.Entry(filter_frame, width=15)
        self.txt_brand.pack(side="left", padx=5)
        tk.Label(filter_frame, text="Encapsulado:").pack(side="left")
        self.txt_pkg = tk.Entry(filter_frame, width=15)
        self.txt_pkg.pack(side="left", padx=5)
        tk.Button(filter_frame, text="Filtrar", bg="#1e50a0", fg="white",
                  command=self._load).pack(side="left", padx=5)
        tk.Button(filter_frame, text="Ver todos", bg="gray", fg="white",
                  command=self._clear).pack(side="left")

        self.lbl_status = tk.Label(self, text="", font=("Arial", 9, "italic"))
        self.lbl_status.pack(anchor="w", padx=12)
        self._observer = ListObserver(self.lbl_status)

        cols = ("ID", "Marca", "Encapsulado", "Voltaje", "Corriente", "Pines", "Ganancia", "Nombres Pines", "Fabricante", "Fecha Registro")
        self.tree = ttk.Treeview(self, columns=cols, show="headings")
        widths = {"ID": 40, "Marca": 100, "Encapsulado": 80, "Voltaje": 65, "Corriente": 70,
                  "Pines": 45, "Ganancia": 70, "Nombres Pines": 130, "Fabricante": 120, "Fecha Registro": 150}
        for c in cols:
            self.tree.heading(c, text=c)
            self.tree.column(c, width=widths.get(c, 100))
        scroll = ttk.Scrollbar(self, orient="vertical", command=self.tree.yview)
        self.tree.configure(yscrollcommand=scroll.set)
        self.tree.pack(fill="both", expand=True, padx=10)
        scroll.pack(side="right", fill="y")

        self._load()
        self._schedule_refresh()

    def _schedule_refresh(self):
        self._load()
        self._job = self.after(3000, self._schedule_refresh)

    def destroy(self):
        self.after_cancel(self._job)
        super().destroy()

    def _clear(self):
        self.txt_brand.delete(0, tk.END)
        self.txt_pkg.delete(0, tk.END)
        self._load()

    def _load(self):
        try:
            brand = self.txt_brand.get().strip() or None
            pkg = self.txt_pkg.get().strip() or None
            data = api.list_active(brand, pkg)
            self.tree.delete(*self.tree.get_children())
            for c in data:
                fab = c["fabricante"]["nombre"] if c.get("fabricante") else "N/A"
                self.tree.insert("", tk.END, values=(
                    c["id"], c["brand"], c["packageType"], c["voltage"],
                    c["current"], c["pinCount"], c["gainFactor"],
                    ", ".join(c["pinNames"]),
                    fab, c.get("registrationDate", "N/A")))
            self._observer.on_data_loaded(len(data), filtered=bool(brand or pkg))
        except Exception:
            pass
