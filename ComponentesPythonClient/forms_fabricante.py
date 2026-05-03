import tkinter as tk
from tkinter import ttk, messagebox
import api_service as api

# ───────────────────────── INSERT FABRICANTE ─────────────────────────
class FormInsertFabricante(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Insertar Fabricante")
        self.resizable(False, False)
        self.grab_set()

        fields = ["ID", "Nombre", "País"]
        self.entries = {}
        for i, f in enumerate(fields):
            tk.Label(self, text=f + ":").grid(row=i, column=0, sticky="e", padx=10, pady=6)
            e = tk.Entry(self, width=28)
            e.grid(row=i, column=1, padx=10, pady=6)
            self.entries[f] = e

        tk.Button(self, text="Guardar", bg="#1e50a0", fg="white",
                  command=self._save).grid(row=3, column=1, pady=10, sticky="e", padx=10)

    def _save(self):
        try:
            e = self.entries
            api.add_fabricante({
                "id": int(e["ID"].get()),
                "nombre": e["Nombre"].get().strip(),
                "pais": e["País"].get().strip()
            })
            messagebox.showinfo("Éxito", "Fabricante insertado correctamente.")
            self.destroy()
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── SEARCH FABRICANTE ─────────────────────────
class FormSearchFabricante(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Consultar Fabricante")
        self.resizable(False, False)
        self.grab_set()

        tk.Label(self, text="ID:").grid(row=0, column=0, padx=10, pady=6, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=6)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        self.result = tk.Text(self, width=40, height=5, state="disabled",
                              bg="#e8e8e8", fg="black", font=("Courier", 10))
        self.result.grid(row=1, column=0, columnspan=3, padx=10, pady=10)

    def _search(self):
        try:
            f = api.get_fabricante(int(self.txt_id.get()))
            self.result.config(state="normal")
            self.result.delete("1.0", tk.END)
            if not f:
                self.result.insert(tk.END, "Fabricante no encontrado.")
            else:
                self.result.insert(tk.END,
                    f"ID:      {f['id']}\nNombre:  {f['nombre']}\nPaís:    {f['pais']}")
            self.result.config(state="disabled")
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── UPDATE FABRICANTE ─────────────────────────
class FormUpdateFabricante(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Actualizar Fabricante")
        self.resizable(False, False)
        self.grab_set()
        self._current = None

        tk.Label(self, text="ID a actualizar:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=10)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        self.edit_frame = tk.Frame(self)
        tk.Label(self.edit_frame, text="Nombre:").grid(row=0, column=0, sticky="e", padx=10, pady=6)
        self.txt_nombre = tk.Entry(self.edit_frame, width=28)
        self.txt_nombre.grid(row=0, column=1, padx=10, pady=6)
        tk.Label(self.edit_frame, text="País:").grid(row=1, column=0, sticky="e", padx=10, pady=6)
        self.txt_pais = tk.Entry(self.edit_frame, width=28)
        self.txt_pais.grid(row=1, column=1, padx=10, pady=6)
        tk.Button(self.edit_frame, text="Guardar cambios", bg="#1e50a0", fg="white",
                  command=self._save).grid(row=2, column=1, pady=10, sticky="e", padx=10)

    def _search(self):
        try:
            self._current = api.get_fabricante(int(self.txt_id.get()))
            if not self._current:
                messagebox.showwarning("No encontrado", "Fabricante no encontrado.")
                self.edit_frame.grid_remove()
                return
            self.txt_nombre.delete(0, tk.END); self.txt_nombre.insert(0, self._current["nombre"])
            self.txt_pais.delete(0, tk.END);   self.txt_pais.insert(0, self._current["pais"])
            self.edit_frame.grid(row=1, column=0, columnspan=3)
        except Exception as ex:
            messagebox.showerror("Error", str(ex))

    def _save(self):
        try:
            self._current.update({
                "nombre": self.txt_nombre.get().strip(),
                "pais":   self.txt_pais.get().strip()
            })
            api.update_fabricante(self._current["id"], self._current)
            messagebox.showinfo("Éxito", "Fabricante actualizado correctamente.")
            self.destroy()
        except Exception as ex:
            messagebox.showerror("Error", str(ex))


# ───────────────────────── DELETE FABRICANTE ─────────────────────────
class FormDeleteFabricante(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Eliminar Fabricante")
        self.resizable(False, False)
        self.grab_set()
        self._found = None

        tk.Label(self, text="ID:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
        self.txt_id = tk.Entry(self, width=20)
        self.txt_id.grid(row=0, column=1, padx=10, pady=10)
        tk.Button(self, text="Buscar", bg="#1e50a0", fg="white",
                  command=self._search).grid(row=0, column=2, padx=10)

        self.result = tk.Text(self, width=40, height=5, state="disabled",
                              bg="#e8e8e8", fg="black", font=("Courier", 10))
        self.result.grid(row=1, column=0, columnspan=3, padx=10, pady=5)

        self.btn_delete = tk.Button(self, text="Eliminar", bg="firebrick", fg="white",
                                    width=20, command=self._delete, state="disabled")
        self.btn_delete.grid(row=2, column=0, columnspan=3, pady=10)

    def _search(self):
        try:
            self._found = api.get_fabricante(int(self.txt_id.get()))
            self.result.config(state="normal")
            self.result.delete("1.0", tk.END)
            if not self._found:
                self.result.insert(tk.END, "Fabricante no encontrado.")
                self.btn_delete.config(state="disabled")
            else:
                self.result.insert(tk.END,
                    f"ID:      {self._found['id']}\nNombre:  {self._found['nombre']}\nPaís:    {self._found['pais']}")
                self.btn_delete.config(state="normal")
            self.result.config(state="disabled")
        except Exception as ex:
            messagebox.showerror("Error", str(ex))

    def _delete(self):
        if not self._found: return
        if messagebox.askyesno("Confirmar", f"¿Eliminar fabricante ID {self._found['id']} ({self._found['nombre']})?"):
            try:
                api.delete_fabricante(self._found["id"])
                messagebox.showinfo("Éxito", "Fabricante eliminado correctamente.")
                self.destroy()
            except Exception as ex:
                messagebox.showerror("Error", str(ex))


# ───────────────────────── LIST FABRICANTE ─────────────────────────
class FormListFabricante(tk.Toplevel):
    def __init__(self, master):
        super().__init__(master)
        self.title("Listar Fabricantes")
        self.geometry("500x350")
        self.grab_set()

        filter_frame = tk.Frame(self)
        filter_frame.pack(fill="x", padx=10, pady=8)
        tk.Label(filter_frame, text="País:").pack(side="left")
        self.txt_pais = tk.Entry(filter_frame, width=15)
        self.txt_pais.pack(side="left", padx=5)
        tk.Button(filter_frame, text="Filtrar", bg="#1e50a0", fg="white",
                  command=self._load).pack(side="left", padx=5)
        tk.Button(filter_frame, text="Ver todos", bg="gray", fg="white",
                  command=self._clear).pack(side="left")

        self.lbl_status = tk.Label(self, text="", font=("Arial", 9, "italic"))
        self.lbl_status.pack(anchor="w", padx=12)

        cols = ("ID", "Nombre", "País")
        self.tree = ttk.Treeview(self, columns=cols, show="headings")
        for c in cols:
            self.tree.heading(c, text=c)
            self.tree.column(c, width=150)
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
        self.txt_pais.delete(0, tk.END)
        self._load()

    def _load(self):
        try:
            pais = self.txt_pais.get().strip() or None
            data = api.list_fabricante(pais)
            self.tree.delete(*self.tree.get_children())
            for f in data:
                self.tree.insert("", tk.END, values=(f["id"], f["nombre"], f["pais"]))
            filtered = bool(pais)
            msg = f"{'Filtro aplicado' if filtered else 'Todos los registros'}: {len(data)} fabricante(s)."
            self.lbl_status.config(text=msg, fg="#1e50a0")
        except Exception:
            pass
