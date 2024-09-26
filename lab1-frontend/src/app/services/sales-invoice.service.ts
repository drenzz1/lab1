import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ClientVendorDto} from "../common/client-vendor-dto";
import {Invoice} from "../common/invoice";

@Injectable({
  providedIn: 'root'
})
export class SalesInvoiceService {
  private apiUrl = 'http://localhost:8080/salesInvoices'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to create a new invoice
  createNewInvoice(): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/create`, {
      headers: this.getHeaders()
    });
  }

  // Method to list client vendors
  listClientVendors(): Observable<ClientVendorDto[]> {
    return this.http.get<ClientVendorDto[]>(`${this.apiUrl}/listVendors`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a sales invoice
  createSalesInvoice(invoiceDto: Invoice): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/create`, invoiceDto, {
      headers: this.getHeaders()
    });
  }
}
