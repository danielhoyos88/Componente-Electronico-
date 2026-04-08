import tkinter as tk
from tkinter import ttk, messagebox
import api_service as api

# ───────────────────────── INSERT PASSIVE ─────────────────────────
class FormInsertPassive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Insertar Componente Pasivo")
        self.resizable(False, False)
        self.grab_set()

        fields = ["ID", "Marca", "Encapsulado", "Voltaje", "Corriente",
                  "Num. Pines", "Tolerancia (%)", "Magnitud Nominal", "Unidad"]
        self.entries = {}
        for i, f in enumerate(fields):
            tk.Label(self, text=f + ":").grid(row=i, column=0, sticky="e", padx=10, pady=4)
            e = tk.Entry(self, width=28)
            e.grid(row=i, column=1, padx=10, pady=4)
            self.entries[f] = e

        tk.Button(self, text="Guardar", bg="#1e50a0", fg="white",
                  command=self._save).grid(row=len(fields), column=1, pady=10, sticky="e", padx=10)

    def _save(self):
        try:
            e = self.entries
            data = {
                "id": int(e["ID"].get()),
                "brand": e["Marca"].get().strip(),
                "packageType": e["Encapsulado"].get().strip(),
                "voltage": float(e["Voltaje"].get()),
                "current": float(e["Corriente"].get()),
                "pinCount": int(e["Num. Pines"].get()),
                "tolerance": float(e["Tolerancia (%)"].get()),
                "nominalMagnitude": float(e["Magnitud Nominal"].get()),
                "nominalUnit": e["Unidad"].get().strip()
            }
            api.add_passive(data)
            messagebox.showinfo("Éxito", "Componente pasivo insertado correctamente.")
            self.destroy()
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── SEARCH PASSIVE ─────────────────────────
class FormSearchPassive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Consultar Componente Pasivo")
        self.resizable(False, False)
        self.grab_set()

        tk.Label(self, text="ID:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=10)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        self.result = tk.Text(self, width=50, height=12, state="disabled", bg="#e8e8e8", fg="black", font=("Courier", 10))
        self.result.grid(row=1, column=0, columnspan=3, padx=10, pady=10)

    def _search(self):
        try:
            c = api.get_passive(int(self.txt_id.get()))
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
                    f"Tolerancia:       ±{c['tolerance']}%\n"
                    f"Valor Nominal:    {c['nominalMagnitude']} {c['nominalUnit']}\n"
                    f"Fecha Registro:   {c.get('registrationDate','N/A')}")
            self.result.config(state="disabled")
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── UPDATE PASSIVE ─────────────────────────
class FormUpdatePassive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Actualizar Componente Pasivo")
        self.resizable(False, False)
        self.grab_set()
        self._current = None

        tk.Label(self, text="ID a actualizar:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=10)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        fields = ["Marca", "Encapsulado", "Voltaje", "Corriente",
                  "Num. Pines", "Tolerancia (%)", "Magnitud Nominal", "Unidad"]
        self.entries = {}
        self.edit_frame = tk.Frame(self)

        for i, f in enumerate(fields):
            tk.Label(self.edit_frame, text=f + ":").grid(row=i, column=0, sticky="e", padx=10, pady=4)
            e = tk.Entry(self.edit_frame, width=28)
            e.grid(row=i, column=1, padx=10, pady=4)
            self.entries[f] = e

        tk.Button(self.edit_frame, text="Guardar cambios", bg="#1e50a0", fg="white",
                  command=self._save).grid(row=len(fields), column=1, pady=10, sticky="e", padx=10)

    def _search(self):
        try:
            self._current = api.get_passive(int(self.txt_id.get()))
            if not self._current:
                messagebox.showwarning("No encontrado", "Componente no encontrado.")
                self.edit_frame.grid_remove()
                return
            c = self._current
            self.entries["Marca"].delete(0, tk.END); self.entries["Marca"].insert(0, c["brand"])
            self.entries["Encapsulado"].delete(0, tk.END); self.entries["Encapsulado"].insert(0, c["packageType"])
            self.entries["Voltaje"].delete(0, tk.END); self.entries["Voltaje"].insert(0, c["voltage"])
            self.entries["Corriente"].delete(0, tk.END); self.entries["Corriente"].insert(0, c["current"])
            self.entries["Num. Pines"].delete(0, tk.END); self.entries["Num. Pines"].insert(0, c["pinCount"])
            self.entries["Tolerancia (%)"].delete(0, tk.END); self.entries["Tolerancia (%)"].insert(0, c["tolerance"])
            self.entries["Magnitud Nominal"].delete(0, tk.END); self.entries["Magnitud Nominal"].insert(0, c["nominalMagnitude"])
            self.entries["Unidad"].delete(0, tk.END); self.entries["Unidad"].insert(0, c["nominalUnit"])
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
                "pinCount": int(e["Num. Pines"].get()),
                "tolerance": float(e["Tolerancia (%)"].get()),
                "nominalMagnitude": float(e["Magnitud Nominal"].get()),
                "nominalUnit": e["Unidad"].get().strip()
            })
            api.update_passive(self._current["id"], self._current)
            messagebox.showinfo("Éxito", "Componente pasivo actualizado correctamente.")
            self.destroy()
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── DELETE PASSIVE ─────────────────────────
class FormDeletePassive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Eliminar Componente Pasivo")
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
            self._found = api.get_passive(int(self.txt_id.get()))
            self.result.config(state="normal")
            self.result.delete("1.0", tk.END)
            if not self._found:
                self.result.insert(tk.END, "Componente no encontrado.")
                self.btn_delete.config(state="disabled")
            else:
                c = self._found
                self.result.insert(tk.END,
                    f"ID:             {c['id']}\n"
                    f"Marca:          {c['brand']}\n"
                    f"Encapsulado:    {c['packageType']}\n"
                    f"Voltaje:        {c['voltage']} V\n"
                    f"Corriente:      {c['current']} A\n"
                    f"Tolerancia:     ±{c['tolerance']}%\n"
                    f"Valor Nominal:  {c['nominalMagnitude']} {c['nominalUnit']}\n"
                    f"Fecha Registro: {c.get('registrationDate','N/A')}")
                self.btn_delete.config(state="normal")
            self.result.config(state="disabled")
        except Exception as ex:
            messagebox.showerror("Error", str(ex))

    def _delete(self):
        if not self._found: return
        if messagebox.askyesno("Confirmar", f"¿Eliminar componente ID {self._found['id']} ({self._found['brand']})?"):
            try:
                api.delete_passive(self._found["id"])
                messagebox.showinfo("Éxito", "Componente eliminado correctamente.")
                self.destroy()
            except Exception as ex:
                messagebox.showerror("Error", str(ex))


# ───────────────────────── LIST PASSIVE ─────────────────────────
class FormListPassive(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Listar Componentes Pasivos")
        self.geometry("900x420")
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

        cols = ("ID", "Marca", "Encapsulado", "Voltaje", "Corriente", "Pines", "Tolerancia", "Magnitud", "Unidad", "Fecha Registro")
        self.tree = ttk.Treeview(self, columns=cols, show="headings")
        for c in cols:
            self.tree.heading(c, text=c)
            self.tree.column(c, width=88)
        scroll = ttk.Scrollbar(self, orient="vertical", command=self.tree.yview)
        self.tree.configure(yscrollcommand=scroll.set)
        self.tree.pack(fill="both", expand=True, padx=10)
        scroll.pack(side="right", fill="y")

        self._load()

    def _clear(self):
        self.txt_brand.delete(0, tk.END)
        self.txt_pkg.delete(0, tk.END)
        self._load()

    def _load(self):
        try:
            brand = self.txt_brand.get().strip() or None
            pkg = self.txt_pkg.get().strip() or None
            data = api.list_passive(brand, pkg)
            self.tree.delete(*self.tree.get_children())
            for c in data:
                self.tree.insert("", tk.END, values=(
                    c["id"], c["brand"], c["packageType"], c["voltage"],
                    c["current"], c["pinCount"], c["tolerance"],
                    c["nominalMagnitude"], c["nominalUnit"],
                    c.get("registrationDate", "N/A")))
        except Exception as ex:
            messagebox.showerror("Error", str(ex))
