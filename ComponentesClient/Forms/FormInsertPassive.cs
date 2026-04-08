using System;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormInsertPassive : Form
    {
        private readonly ApiService _api = new();
        private TextBox txtId, txtBrand, txtPackage, txtVoltage, txtCurrent, txtPinCount, txtTolerance, txtMagnitude, txtUnit;

        public FormInsertPassive()
        {
            Text = "Insertar Componente Pasivo";
            Size = new Size(420, 460);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            var panel = new TableLayoutPanel
            {
                Dock = DockStyle.Fill,
                ColumnCount = 2,
                RowCount = 10,
                Padding = new Padding(15)
            };
            panel.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 40));
            panel.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 60));

            txtId = new TextBox(); txtBrand = new TextBox(); txtPackage = new TextBox();
            txtVoltage = new TextBox(); txtCurrent = new TextBox(); txtPinCount = new TextBox();
            txtTolerance = new TextBox(); txtMagnitude = new TextBox(); txtUnit = new TextBox();

            string[] labels = { "ID:", "Marca:", "Encapsulado:", "Voltaje:", "Corriente:", "Num. Pines:", "Tolerancia (%):", "Magnitud Nominal:", "Unidad:" };
            TextBox[] fields = { txtId, txtBrand, txtPackage, txtVoltage, txtCurrent, txtPinCount, txtTolerance, txtMagnitude, txtUnit };

            for (int i = 0; i < labels.Length; i++)
            {
                panel.Controls.Add(new Label { Text = labels[i], Anchor = AnchorStyles.Right, TextAlign = ContentAlignment.MiddleRight }, 0, i);
                fields[i].Dock = DockStyle.Fill;
                panel.Controls.Add(fields[i], 1, i);
            }

            var btnSave = new Button { Text = "Guardar", Dock = DockStyle.Fill, BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat };
            btnSave.Click += BtnSave_Click;
            panel.Controls.Add(btnSave, 1, 9);

            Controls.Add(panel);
        }

        private async void BtnSave_Click(object? sender, EventArgs e)
        {
            try
            {
                var c = new PassiveComponent
                {
                    Id = int.Parse(txtId.Text.Trim()),
                    Brand = txtBrand.Text.Trim(),
                    PackageType = txtPackage.Text.Trim(),
                    Voltage = double.Parse(txtVoltage.Text.Trim()),
                    Current = double.Parse(txtCurrent.Text.Trim()),
                    PinCount = int.Parse(txtPinCount.Text.Trim()),
                    Tolerance = double.Parse(txtTolerance.Text.Trim()),
                    NominalMagnitude = double.Parse(txtMagnitude.Text.Trim()),
                    NominalUnit = txtUnit.Text.Trim()
                };
                await _api.AddPassiveAsync(c);
                MessageBox.Show("Componente pasivo insertado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
