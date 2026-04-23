import urllib.request
import urllib.error
import json

BASE_URL = "http://localhost:8080"

def _request(method, path, data=None):
    url = BASE_URL + path
    body = json.dumps(data).encode() if data else None
    req = urllib.request.Request(url, data=body, method=method)
    req.add_header("Content-Type", "application/json")
    try:
        with urllib.request.urlopen(req) as res:
            return json.loads(res.read().decode())
    except urllib.error.HTTPError as e:
        if e.code == 404:
            return None
        raise Exception(e.read().decode())

def add_passive(c):        return _request("POST", "/api/passive", c)
def get_passive(id):       return _request("GET", f"/api/passive/{id}")
def search_passive(id=None, brand=None):
    q = []
    if id is not None: q.append(f"id={id}")
    if brand:          q.append(f"brand={brand}")
    return _request("GET", "/api/passive/search" + ("?" + "&".join(q) if q else ""))
def list_passive(brand=None, pkg=None):
    q = []
    if brand: q.append(f"brand={brand}")
    if pkg:   q.append(f"packageType={pkg}")
    return _request("GET", "/api/passive" + ("?" + "&".join(q) if q else ""))
def update_passive(id, c): return _request("PUT", f"/api/passive/{id}", c)
def delete_passive(id):    return _request("DELETE", f"/api/passive/{id}")

def add_active(c):         return _request("POST", "/api/active", c)
def get_active(id):        return _request("GET", f"/api/active/{id}")
def search_active(id=None, brand=None):
    q = []
    if id is not None: q.append(f"id={id}")
    if brand:          q.append(f"brand={brand}")
    return _request("GET", "/api/active/search" + ("?" + "&".join(q) if q else ""))
def list_active(brand=None, pkg=None):
    q = []
    if brand: q.append(f"brand={brand}")
    if pkg:   q.append(f"packageType={pkg}")
    return _request("GET", "/api/active" + ("?" + "&".join(q) if q else ""))
def update_active(id, c):  return _request("PUT", f"/api/active/{id}", c)
def delete_active(id):     return _request("DELETE", f"/api/active/{id}")
