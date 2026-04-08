using System;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormUpdatePassive : Form
    {
        private readonly ApiService _api = new();
        private PassiveComponent? _current;

        private TextBox txtSearchId, txtBrand, txtPackage, txtVoltage, txtCurrent, txtPinCount, txtTolerance, txtMagnitude, txtUnit;
        private Panel _editPanel;
        private Button btnSave;

        public FormUpdatePassive()
        {
            Text = "Actualizar Componente Pasivo";
            Size = new Size(430, 530);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            // --- Search row ---
            var searchPanel = new Panel { Left = 0, Top = 0, Width = 430, Height = 60, Padding = new Padding(15, 10, 15, 0) };
            var lblId = new Label { Text = "ID a actualizar:", Left = 15, Top = 15, AutoSize = true };
            txtSearchId = new TextBox { Left = 15, Top = 35, Width = 180 };
            var btnSearch = new Button
            {
                Text = "Buscar", Left = 205, Top = 32, Width = 80,
                BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat
            };
            btnSearch.Click += BtnSearch_Click;
            searchPanel.Controls.AddRange(new Control[] { lblId, txtSearchId, btnSearch });

            // --- Edit panel (hidden until found) ---
            _editPanel = new Panel { Left = 0, Top = 65, Width = 430, Visible = false };

            var tbl = new TableLayoutPanel
            {
                Dock = DockStyle.Fill, ColumnCount = 2, RowCount = 9, Padding = new Padding(15)
            };
            tbl.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 42));
            tbl.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 58));

            txtBrand = new TextBox(); txtPackage = new TextBox(); txtVoltage = new TextBox();
            txtCurrent = new TextBox(); txtPinCount = new TextBox(); txtTolerance = new TextBox();
            txtMagnitude = new TextBox(); txtUnit = new TextBox();

            string[] labels = { "Marca:", "Encapsulado:", "Voltaje:", "Corriente:", "Num. Pines:", "Tolerancia (%):", "Magnitud Nominal:", "Unidad:" };
            TextBox[] fields = { txtBrand, txtPackage, txtVoltage, txtCurrent, txtPinCount, txtTolerance, txtMagnitude, txtUnit };

            for (int i = 0; i < labels.Length; i++)
            {
                tbl.Controls.Add(new Label { Text = labels[i], Anchor = AnchorStyles.Right, TextAlign = ContentAlignment.MiddleRight }, 0, i);
                fields[i].Dock = DockStyle.Fill;
                tbl.Controls.Add(fields[i], 1, i);
            }

            btnSave = new Button { Text = "Guardar cambios", Dock = DockStyle.Fill, BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat };
            btnSave.Click += BtnSave_Click;
            tbl.Controls.Add(btnSave, 1, 8);

            _editPanel.Controls.Add(tbl);
            _editPanel.Height = 400;

            Controls.AddRange(new Control[] { searchPanel, _editPanel });
        }

        private async void BtnSearch_Click(object? sender, EventArgs e)
        {
            if (!int.TryParse(txtSearchId.Text.Trim(), out int id)) { MessageBox.Show("ID inválido."); return; }
            try
            {
                _current = await _api.GetPassiveByIdAsync(id);
                if (_current == null) { MessageBox.Show("Componente no encontrado."); _editPanel.Visible = false; return; }

                txtBrand.Text = _current.Brand;
                txtPackage.Text = _current.PackageType;
                txtVoltage.Text = _current.Voltage.ToString();
                txtCurrent.Text = _current.Current.ToString();
                txtPinCount.Text = _current.PinCount.ToString();
                txtTolerance.Text = _current.Tolerance.ToString();
                txtMagnitude.Text = _current.NominalMagnitude.ToString();
                txtUnit.Text = _current.NominalUnit;
                _editPanel.Visible = true;
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }

        private async void BtnSave_Click(object? sender, EventArgs e)
        {
            if (_current == null) return;
            try
            {
                _current.Brand = txtBrand.Text.Trim();
                _current.PackageType = txtPackage.Text.Trim();
                _current.Voltage = double.Parse(txtVoltage.Text.Trim());
                _current.Current = double.Parse(txtCurrent.Text.Trim());
                _current.PinCount = int.Parse(txtPinCount.Text.Trim());
                _current.Tolerance = double.Parse(txtTolerance.Text.Trim());
                _current.NominalMagnitude = double.Parse(txtMagnitude.Text.Trim());
                _current.NominalUnit = txtUnit.Text.Trim();

                await _api.UpdatePassiveAsync(_current.Id, _current);
                MessageBox.Show("Componente pasivo actualizado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Close();
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }
    }
}
