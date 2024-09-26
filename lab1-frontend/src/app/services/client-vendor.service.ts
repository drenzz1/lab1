import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ClientVendorDto} from "../common/client-vendor-dto";

@Injectable({
  providedIn: 'root'
})
export class ClientVendorService {
  private apiUrl = 'http://localhost:8080/api/client-vendors'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get all client vendors
  getAllClientVendors(): Observable<ClientVendorDto[]> {
    return this.http.get<ClientVendorDto[]>(`${this.apiUrl}/list`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new client vendor
  createClientVendor(clientVendorDto: ClientVendorDto): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/create`, clientVendorDto, {
      headers: this.getHeaders()
    });
  }

  // Method to update an existing client vendor
  editClientVendor(id: number, clientVendorDto: ClientVendorDto): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/edit/${id}`, clientVendorDto, {
      headers: this.getHeaders()
    });
  }

  // Method to delete a client vendor by ID
  deleteClientVendor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }
}
