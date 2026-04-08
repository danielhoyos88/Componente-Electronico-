using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using ComponentesClient.Models;
using ComponentesClient.Services;

namespace ComponentesClient.Forms
{
    public class FormListActive : Form
    {
        private readonly ApiService _api = new();
        private TextBox txtBrand, txtPackage;
        private DataGridView grid;

        public FormListActive()
        {
            Text = "Listar Componentes Activos";
            Size = new Size(950, 500);
            StartPosition = FormStartPosition.CenterScreen;
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            var filterPanel = new Panel { Dock = DockStyle.Top, Height = 55, Padding = new Padding(10, 8, 10, 0) };

            var lblBrand = new Label { Text = "Marca:", Left = 10, Top = 15, AutoSize = true };
            txtBrand = new TextBox { Left = 60, Top = 12, Width = 150 };
            var lblPkg = new Label { Text = "Encapsulado:", Left = 225, Top = 15, AutoSize = true };
            txtPackage = new TextBox { Left = 320, Top = 12, Width = 150 };

            var btnFilter = new Button
            {
                Text = "Filtrar", Left = 485, Top = 10, Width = 80,
                BackColor = Color.FromArgb(30, 80, 160), ForeColor = Color.White, FlatStyle = FlatStyle.Flat
            };
            btnFilter.Click += async (s, e) => await LoadData();

            var btnAll = new Button
            {
                Text = "Ver todos", Left = 575, Top = 10, Width = 90,
                BackColor = Color.Gray, ForeColor = Color.White, FlatStyle = FlatStyle.Flat
            };
            btnAll.Click += (s, e) => { txtBrand.Clear(); txtPackage.Clear(); _ = LoadData(); };

            filterPanel.Controls.AddRange(new Control[] { lblBrand, txtBrand, lblPkg, txtPackage, btnFilter, btnAll });

            grid = new DataGridView
            {
                Dock = DockStyle.Fill,
                ReadOnly = true,
                AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill,
                AllowUserToAddRows = false,
                BackgroundColor = Color.White,
                BorderStyle = BorderStyle.None,
                RowHeadersVisible = false
            };

            Controls.Add(grid);
            Controls.Add(filterPanel);

            Load += async (s, e) => await LoadData();
        }

        private async System.Threading.Tasks.Task LoadData()
        {
            try
            {
                string? brand = string.IsNullOrWhiteSpace(txtBrand.Text) ? null : txtBrand.Text.Trim();
                string? pkg = string.IsNullOrWhiteSpace(txtPackage.Text) ? null : txtPackage.Text.Trim();
                List<ActiveComponent> list = await _api.GetAllActiveAsync(brand, pkg);

                grid.DataSource = list.Count > 0 ? list : new List<ActiveComponent>();

                if (grid.Columns.Count > 0)
                {
                    grid.Columns["Id"].HeaderText = "ID";
                    grid.Columns["Brand"].HeaderText = "Marca";
                    grid.Columns["PackageType"].HeaderText = "Encapsulado";
                    grid.Columns["Voltage"].HeaderText = "Voltaje (V)";
                    grid.Columns["Current"].HeaderText = "Corriente (A)";
                    grid.Columns["PinCount"].HeaderText = "Pines";
                    grid.Columns["GainFactor"].HeaderText = "Ganancia";
                    grid.Columns["PinNames"].HeaderText = "Nombres Pines";
                    grid.Columns["RegistrationDate"].HeaderText = "Fecha Registro";
                }
            }
            catch (Exception ex) { MessageBox.Show("Error: " + ex.Message); }
        }
    }
}
