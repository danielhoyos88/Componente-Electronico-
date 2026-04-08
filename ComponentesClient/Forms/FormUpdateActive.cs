using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormUpdateActive : Form
    {
        private readonly ApiService _api = new();
        private ActiveComponent? _current;

        private TextBox txtSearchId, txtBrand, txtPackage, txtVoltage, txtCurrent, txtGain, txtPins;
        private Panel _editPanel;

        public FormUpdateActive()
        {
            Text = "Actualizar Componente Activo";
            Size = new Size(430, 480);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

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

            _editPanel = new Panel { Left = 0, Top = 65, Width = 430, Visible = false };

            var tbl = new TableLayoutPanel
            {
                Dock = DockStyle.Fill, ColumnCount = 2, RowCount = 8, Padding = new Padding(15)
            };
            tbl.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 42));
            tbl.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 58));

            txtBrand = new TextBox(); txtPackage = new TextBox(); txtVoltage = new TextBox();
            txtCurrent = new TextBox(); txtGain = new TextBox(); txtPins = new TextBox();

            string[] labels = { "Marca:", "Encapsulado:", "Voltaje:", "Corriente:", "Factor Ganancia:", "Pines (coma):" };
            TextBox[] fields = { txtBrand, txtPackage, txtVoltage, txtCurrent, txtGain, txtPins };

            for (int i = 0; i < labels.Length; i++)
            {
                tbl.Controls.Add(new Label { Text = labels[i], Anchor = AnchorStyles.Right, TextAlign = ContentAlignment.MiddleRight }, 0, i);
                fields[i].Dock = DockStyle.Fill;
                tbl.Controls.Add(fields[i], 1, i);
            }

            var btnSave = new Button { Text = "Guardar cambios", Dock = DockStyle.Fill, BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat };
            btnSave.Click += BtnSave_Click;
            tbl.Controls.Add(btnSave, 1, 7);

            _editPanel.Controls.Add(tbl);
            _editPanel.Height = 380;

            Controls.AddRange(new Control[] { searchPanel, _editPanel });
        }

        private async void BtnSearch_Click(object? sender, EventArgs e)
        {
            if (!int.TryParse(txtSearchId.Text.Trim(), out int id)) { MessageBox.Show("ID inválido."); return; }
            try
            {
                _current = await _api.GetActiveByIdAsync(id);
                if (_current == null) { MessageBox.Show("Componente no encontrado."); _editPanel.Visible = false; return; }

                txtBrand.Text = _current.Brand;
                txtPackage.Text = _current.PackageType;
                txtVoltage.Text = _current.Voltage.ToString();
                txtCurrent.Text = _current.Current.ToString();
                txtGain.Text = _current.GainFactor.ToString();
                txtPins.Text = string.Join(",", _current.PinNames);
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
                _current.GainFactor = double.Parse(txtGain.Text.Trim());
                _current.PinNames = new List<string>(txtPins.Text.Trim().Split(','));

                await _api.UpdateActiveAsync(_current.Id, _current);
                MessageBox.Show("Componente activo actualizado correctamente.", "Éxito", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Close();
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }
    }
}
