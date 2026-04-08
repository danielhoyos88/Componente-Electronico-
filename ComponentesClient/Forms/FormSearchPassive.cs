using System;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormSearchPassive : Form
    {
        private readonly ApiService _api = new();
        private TextBox txtId;
        private RichTextBox txtResult;

        public FormSearchPassive()
        {
            Text = "Consultar Componente Pasivo";
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

            txtResult = new RichTextBox
            {
                Left = 15, Top = 90, Width = 370, Height = 230,
                ReadOnly = true, BackColor = Color.WhiteSmoke, Font = new Font("Consolas", 10)
            };

            panel.Controls.AddRange(new Control[] { lblId, txtId, btnSearch, txtResult });
            Controls.Add(panel);
        }

        private async void BtnSearch_Click(object? sender, EventArgs e)
        {
            if (!int.TryParse(txtId.Text.Trim(), out int id)) { MessageBox.Show("ID inválido."); return; }
            try
            {
                PassiveComponent? c = await _api.GetPassiveByIdAsync(id);
                if (c == null) { txtResult.Text = "Componente no encontrado."; return; }
                txtResult.Text =
                    $"ID:               {c.Id}\n" +
                    $"Marca:            {c.Brand}\n" +
                    $"Encapsulado:      {c.PackageType}\n" +
                    $"Voltaje:          {c.Voltage} V\n" +
                    $"Corriente:        {c.Current} A\n" +
                    $"Num. Pines:       {c.PinCount}\n" +
                    $"Tolerancia:       ±{c.Tolerance}%\n" +
                    $"Valor Nominal:    {c.NominalMagnitude} {c.NominalUnit}\n" +
                    $"Fecha Registro:   {c.RegistrationDate:yyyy-MM-dd HH:mm:ss}";
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }
    }
}
