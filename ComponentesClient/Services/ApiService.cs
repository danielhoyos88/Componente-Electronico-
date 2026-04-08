using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using ComponentesClient.Models;
using Newtonsoft.Json;

namespace ComponentesClient.Services
{
    public class ApiService
    {
        private static readonly HttpClient _http = new HttpClient { BaseAddress = new Uri("http://localhost:8080") };

        private static T Deserialize<T>(string json) =>
            JsonConvert.DeserializeObject<T>(json)!;

        private static StringContent ToJson(object obj) =>
            new StringContent(JsonConvert.SerializeObject(obj), Encoding.UTF8, "application/json");

        // ===== PASSIVE =====

        public async Task<PassiveComponent> AddPassiveAsync(PassiveComponent c)
        {
            var res = await _http.PostAsync("/api/passive", ToJson(c));
            res.EnsureSuccessStatusCode();
            return Deserialize<PassiveComponent>(await res.Content.ReadAsStringAsync());
        }

        public async Task<PassiveComponent?> GetPassiveByIdAsync(int id)
        {
            var res = await _http.GetAsync($"/api/passive/{id}");
            if (res.StatusCode == System.Net.HttpStatusCode.NotFound) return null;
            res.EnsureSuccessStatusCode();
            return Deserialize<PassiveComponent>(await res.Content.ReadAsStringAsync());
        }

        public async Task<List<PassiveComponent>> GetAllPassiveAsync(string? brand = null, string? packageType = null)
        {
            var query = BuildQuery(brand, packageType);
            var res = await _http.GetAsync($"/api/passive{query}");
            res.EnsureSuccessStatusCode();
            return Deserialize<List<PassiveComponent>>(await res.Content.ReadAsStringAsync());
        }

        public async Task<PassiveComponent?> UpdatePassiveAsync(int id, PassiveComponent c)
        {
            var res = await _http.PutAsync($"/api/passive/{id}", ToJson(c));
            if (res.StatusCode == System.Net.HttpStatusCode.NotFound) return null;
            res.EnsureSuccessStatusCode();
            return Deserialize<PassiveComponent>(await res.Content.ReadAsStringAsync());
        }

        public async Task<PassiveComponent?> DeletePassiveAsync(int id)
        {
            var res = await _http.DeleteAsync($"/api/passive/{id}");
            if (res.StatusCode == System.Net.HttpStatusCode.NotFound) return null;
            res.EnsureSuccessStatusCode();
            return Deserialize<PassiveComponent>(await res.Content.ReadAsStringAsync());
        }

        // ===== ACTIVE =====

        public async Task<ActiveComponent> AddActiveAsync(ActiveComponent c)
        {
            var res = await _http.PostAsync("/api/active", ToJson(c));
            res.EnsureSuccessStatusCode();
            return Deserialize<ActiveComponent>(await res.Content.ReadAsStringAsync());
        }

        public async Task<ActiveComponent?> GetActiveByIdAsync(int id)
        {
            var res = await _http.GetAsync($"/api/active/{id}");
            if (res.StatusCode == System.Net.HttpStatusCode.NotFound) return null;
            res.EnsureSuccessStatusCode();
            return Deserialize<ActiveComponent>(await res.Content.ReadAsStringAsync());
        }

        public async Task<List<ActiveComponent>> GetAllActiveAsync(string? brand = null, string? packageType = null)
        {
            var query = BuildQuery(brand, packageType);
            var res = await _http.GetAsync($"/api/active{query}");
            res.EnsureSuccessStatusCode();
            return Deserialize<List<ActiveComponent>>(await res.Content.ReadAsStringAsync());
        }

        public async Task<ActiveComponent?> UpdateActiveAsync(int id, ActiveComponent c)
        {
            var res = await _http.PutAsync($"/api/active/{id}", ToJson(c));
            if (res.StatusCode == System.Net.HttpStatusCode.NotFound) return null;
            res.EnsureSuccessStatusCode();
            return Deserialize<ActiveComponent>(await res.Content.ReadAsStringAsync());
        }

        public async Task<ActiveComponent?> DeleteActiveAsync(int id)
        {
            var res = await _http.DeleteAsync($"/api/active/{id}");
            if (res.StatusCode == System.Net.HttpStatusCode.NotFound) return null;
            res.EnsureSuccessStatusCode();
            return Deserialize<ActiveComponent>(await res.Content.ReadAsStringAsync());
        }

        // ===== UTIL =====

        private static string BuildQuery(string? brand, string? packageType)
        {
            var parts = new List<string>();
            if (!string.IsNullOrWhiteSpace(brand)) parts.Add($"brand={Uri.EscapeDataString(brand)}");
            if (!string.IsNullOrWhiteSpace(packageType)) parts.Add($"packageType={Uri.EscapeDataString(packageType)}");
            return parts.Count > 0 ? "?" + string.Join("&", parts) : "";
        }
    }
}
