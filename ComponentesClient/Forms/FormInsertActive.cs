using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormInsertActive : Form
    {
        private readonly ApiService _api = new();
        private TextBox txtId, txtBrand, txtPackage, txtVoltage, txtCurrent, txtGain, txtPins;

        public FormInsertActive()
        {
            Text = "Insertar Componente Activo";
            Size = new Size(420, 400);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            var tbl = new TableLayoutPanel
            {
                Dock = DockStyle.Fill, ColumnCount = 2, RowCount = 8, Padding = new Padding(15)
            };
            tbl.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 42));
            tbl.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 58));

            txtId = new TextBox(); txtBrand = new TextBox(); txtPackage = new TextBox();
            txtVoltage = new TextBox(); txtCurrent = new TextBox(); txtGain = new TextBox();
            txtPins = new TextBox { PlaceholderText = "ej: Base,Colector,Emisor" };

            string[] labels = { "ID:", "Marca:", "Encapsulado:", "Voltaje:", "Corriente:", "Factor Ganancia:", "Pines (separados por coma):" };
            TextBox[] fields = { txtId, txtBrand, txtPackage, txtVoltage, txtCurrent, txtGain, txtPins };

            for (int i = 0; i < labels.Length; i++)
            {
                tbl.Controls.Add(new Label { Text = labels[i], Anchor = AnchorStyles.Right, TextAlign = ContentAlignment.MiddleRight }, 0, i);
                fields[i].Dock = DockStyle.Fill;
                tbl.Controls.Add(fields[i], 1, i);
            }

            var btnSave = new Button { Text = "Guardar", Dock = DockStyle.Fill, BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat };
            btnSave.Click += BtnSave_Click;
            tbl.Controls.Add(btnSave, 1, 7);

            Controls.Add(tbl);
        }

        private async void BtnSave_Click(object? sender, EventArgs e)
        {
            try
            {
                var pins = new List<string>(txtPins.Text.Trim().Split(','));
                var c = new ActiveComponent
                {
                    Id = int.Parse(txtId.Text.Trim()),
                    Brand = txtBrand.Text.Trim(),
                    PackageType = txtPackage.Text.Trim(),
                    Voltage = double.Parse(txtVoltage.Text.Trim()),
                    Current = double.Parse(txtCurrent.Text.Trim()),
                    GainFactor = double.Parse(txtGain.Text.Trim()),
                    PinNames = pins
                };
                await _api.AddActiveAsync(c);
                MessageBox.Show("Componente activo insertado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Close();
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }
    }
}
