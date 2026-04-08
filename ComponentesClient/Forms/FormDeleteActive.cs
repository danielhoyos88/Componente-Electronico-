using System;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormDeleteActive : Form
    {
        private readonly ApiService _api = new();
        private ActiveComponent? _found;
        private TextBox txtId;
        private RichTextBox txtInfo;
        private Button btnDelete;

        public FormDeleteActive()
        {
            Text = "Eliminar Componente Activo";
            Size = new Size(420, 380);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            var panel = new Panel { Dock = DockStyle.Fill, Padding = new Padding(15) };

            var lblId = new Label { Text = "ID del componente:", Left = 15, Top = 20, AutoSize = true };
            txtId = new TextBox { Left = 15, Top = 45, Width = 200 };
            var btnSearch = new Button
            {
                Text = "Buscar", Left = 225, Top = 42, Width = 80,
                BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat
            };
            btnSearch.Click += BtnSearch_Click;

            txtInfo = new RichTextBox
            {
                Left = 15, Top = 90, Width = 370, Height = 190,
                ReadOnly = true, BackColor = Color.WhiteSmoke, Font = new Font("Consolas", 10)
            };

            btnDelete = new Button
            {
                Text = "Eliminar", Left = 15, Top = 295, Width = 370, Height = 35,
                BackColor = Color.Firebrick, ForeColor = Color.White, FlatStyle = FlatStyle.Flat,
                Enabled = false
            };
            btnDelete.Click += BtnDelete_Click;

            panel.Controls.AddRange(new Control[] { lblId, txtId, btnSearch, txtInfo, btnDelete });
            Controls.Add(panel);
        }

        private async void BtnSearch_Click(object? sender, EventArgs e)
        {
            if (!int.TryParse(txtId.Text.Trim(), out int id)) { MessageBox.Show("ID inválido."); return; }
            try
            {
                _found = await _api.GetActiveByIdAsync(id);
                if (_found == null) { txtInfo.Text = "Componente no encontrado."; btnDelete.Enabled = false; return; }
                txtInfo.Text =
                    $"ID:               {_found.Id}\n" +
                    $"Marca:            {_found.Brand}\n" +
                    $"Encapsulado:      {_found.PackageType}\n" +
                    $"Voltaje:          {_found.Voltage} V\n" +
                    $"Corriente:        {_found.Current} A\n" +
                    $"Factor Ganancia:  {_found.GainFactor}\n" +
                    $"Pines:            {string.Join(", ", _found.PinNames)}\n" +
                    $"Fecha Registro:   {_found.RegistrationDate:yyyy-MM-dd HH:mm:ss}";
                btnDelete.Enabled = true;
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }

        private async void BtnDelete_Click(object? sender, EventArgs e)
        {
            if (_found == null) return;
            var confirm = MessageBox.Show($"¿Eliminar componente ID {_found.Id} ({_found.Brand})?",
                "Confirmar eliminación", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (confirm != DialogResult.Yes) return;
            try
            {
                await _api.DeleteActiveAsync(_found.Id);
                MessageBox.Show("Componente eliminado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Close();
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }
    }
}
