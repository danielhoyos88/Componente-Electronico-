using System;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Forms;

namespace ComponentesClient
{
    public class FormMain : Form
    {
        public FormMain()
        {
            Text = "Electronic Components Manager";
            Size = new Size(650, 420);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedSingle;
            MaximizeBox = false;
            BackColor = Color.FromArgb(240, 248, 255);

            BuildMenu();
            BuildCenterPanel();
        }

        private void BuildMenu()
        {
            var menuBar = new MenuStrip();

            // ---- Componente Pasivo ----
            var passiveMenu = new ToolStripMenuItem("Componente Pasivo");
            passiveMenu.DropDownItems.Add("Insertar", null, (s, e) => new FormInsertPassive().ShowDialog());
            passiveMenu.DropDownItems.Add("Consultar", null, (s, e) => new FormSearchPassive().ShowDialog());
            passiveMenu.DropDownItems.Add("Actualizar", null, (s, e) => new FormUpdatePassive().ShowDialog());
            passiveMenu.DropDownItems.Add("Eliminar", null, (s, e) => new FormDeletePassive().ShowDialog());
            passiveMenu.DropDownItems.Add("Listar", null, (s, e) => new FormListPassive().ShowDialog());

            // ---- Componente Activo ----
            var activeMenu = new ToolStripMenuItem("Componente Activo");
            activeMenu.DropDownItems.Add("Insertar", null, (s, e) => new FormInsertActive().ShowDialog());
            activeMenu.DropDownItems.Add("Consultar", null, (s, e) => new FormSearchActive().ShowDialog());
            activeMenu.DropDownItems.Add("Actualizar", null, (s, e) => new FormUpdateActive().ShowDialog());
            activeMenu.DropDownItems.Add("Eliminar", null, (s, e) => new FormDeleteActive().ShowDialog());
            activeMenu.DropDownItems.Add("Listar", null, (s, e) => new FormListActive().ShowDialog());

            // ---- Ayuda ----
            var helpMenu = new ToolStripMenuItem("Ayuda");
            helpMenu.DropDownItems.Add("Acerca de...", null, (s, e) =>
                MessageBox.Show(
                    "Electronic Components Manager\nVersión: 1.0\n\nIntegrantes:\n  - Daniel Hoyos",
                    "Acerca de...", MessageBoxButtons.OK, MessageBoxIcon.Information));

            menuBar.Items.Add(passiveMenu);
            menuBar.Items.Add(activeMenu);
            menuBar.Items.Add(helpMenu);
            MainMenuStrip = menuBar;
            Controls.Add(menuBar);
        }

        private void BuildCenterPanel()
        {
            var panel = new Panel { Dock = DockStyle.Fill, BackColor = Color.FromArgb(240, 248, 255) };

            var lblTitle = new Label
            {
                Text = "Electronic Components Manager",
                Font = new Font("Arial", 20, FontStyle.Bold),
                AutoSize = true,
                ForeColor = Color.FromArgb(30, 80, 160)
            };

            var lblVersion = new Label
            {
                Text = "Versión 1.0",
                Font = new Font("Arial", 11),
                AutoSize = true,
                ForeColor = Color.Gray
            };

            var lblTeam = new Label
            {
                Text = "Integrante: Daniel Hoyos",
                Font = new Font("Arial", 11),
                AutoSize = true,
                ForeColor = Color.Gray
            };

            var lblHint = new Label
            {
                Text = "Usa el menú superior para navegar entre funcionalidades.",
                Font = new Font("Arial", 10, FontStyle.Italic),
                AutoSize = true,
                ForeColor = Color.DimGray
            };

            panel.Controls.AddRange(new Control[] { lblTitle, lblVersion, lblTeam, lblHint });

            panel.Resize += (s, e) =>
            {
                int cx = panel.Width / 2;
                lblTitle.Left = cx - lblTitle.Width / 2;
                lblTitle.Top = 80;
                lblVersion.Left = cx - lblVersion.Width / 2;
                lblVersion.Top = 130;
                lblTeam.Left = cx - lblTeam.Width / 2;
                lblTeam.Top = 160;
                lblHint.Left = cx - lblHint.Width / 2;
                lblHint.Top = 210;
            };

            Controls.Add(panel);
        }
    }
}
