using System;
using System.Collections.Generic;

namespace ComponentesClient.Models
{
    public class PassiveComponent
    {
        public int Id { get; set; }
        public string Brand { get; set; } = "";
        public string PackageType { get; set; } = "";
        public double Voltage { get; set; }
        public double Current { get; set; }
        public int PinCount { get; set; }
        public double Tolerance { get; set; }
        public double NominalMagnitude { get; set; }
        public string NominalUnit { get; set; } = "";
        public DateTime RegistrationDate { get; set; }
    }

    public class ActiveComponent
    {
        public int Id { get; set; }
        public string Brand { get; set; } = "";
        public string PackageType { get; set; } = "";
        public double Voltage { get; set; }
        public double Current { get; set; }
        public int PinCount { get; set; }
        public double GainFactor { get; set; }
        public List<string> PinNames { get; set; } = new();
        public DateTime RegistrationDate { get; set; }
    }
}
