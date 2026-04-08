import tkinter as tk
from tkinter import messagebox
from forms_passive import (FormInsertPassive, FormSearchPassive,
                            FormUpdatePassive, FormDeletePassive, FormListPassive)
from forms_active import (FormInsertActive, FormSearchActive,
                           FormUpdateActive, FormDeleteActive, FormListActive)

class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Electronic Components Manager")
        self.geometry("600x380")
        self.resizable(False, False)
        self.configure(bg="#f0f8ff")
        self._build_menu()
        self._build_center()

    def _build_menu(self):
        menubar = tk.Menu(self)

        # Componente Pasivo
        passive_menu = tk.Menu(menubar, tearoff=0)
        passive_menu.add_command(label="Insertar",  command=lambda: FormInsertPassive(self))
        passive_menu.add_command(label="Consultar", command=lambda: FormSearchPassive(self))
        passive_menu.add_command(label="Actualizar",command=lambda: FormUpdatePassive(self))
        passive_menu.add_command(label="Eliminar",  command=lambda: FormDeletePassive(self))
        passive_menu.add_command(label="Listar",    command=lambda: FormListPassive(self))
        menubar.add_cascade(label="Componente Pasivo", menu=passive_menu)

        # Componente Activo
        active_menu = tk.Menu(menubar, tearoff=0)
        active_menu.add_command(label="Insertar",  command=lambda: FormInsertActive(self))
        active_menu.add_command(label="Consultar", command=lambda: FormSearchActive(self))
        active_menu.add_command(label="Actualizar",command=lambda: FormUpdateActive(self))
        active_menu.add_command(label="Eliminar",  command=lambda: FormDeleteActive(self))
        active_menu.add_command(label="Listar",    command=lambda: FormListActive(self))
        menubar.add_cascade(label="Componente Activo", menu=active_menu)

        # Ayuda
        help_menu = tk.Menu(menubar, tearoff=0)
        help_menu.add_command(label="Acerca de...", command=self._about)
        menubar.add_cascade(label="Ayuda", menu=help_menu)

        self.config(menu=menubar)

    def _build_center(self):
        tk.Label(self, text="Electronic Components Manager",
                 font=("Arial", 20, "bold"), fg="#1e50a0", bg="#f0f8ff").pack(pady=(60, 10))
        tk.Label(self, text="Versión 1.0",
                 font=("Arial", 11), fg="gray", bg="#f0f8ff").pack()
        tk.Label(self, text="Integrante: Daniel Hoyos",
                 font=("Arial", 11), fg="gray", bg="#f0f8ff").pack(pady=5)
        tk.Label(self, text="Usa el menú superior para navegar entre funcionalidades.",
                 font=("Arial", 10, "italic"), fg="dimgray", bg="#f0f8ff").pack(pady=20)

    def _about(self):
        messagebox.showinfo("Acerca de...",
            "Electronic Components Manager\nVersión: 1.0\n\nIntegrantes:\n  - Daniel Hoyos")

if __name__ == "__main__":
    App().mainloop()
