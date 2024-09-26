import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InvoiceProduct} from "../common/invoice-product";

@Injectable({
  providedIn: 'root'
})
export class ReportingService {
  private apiUrl = 'http://localhost:8080/api/reports'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get stock data
  getStockData(): Observable<InvoiceProduct[]> {
    return this.http.get<InvoiceProduct[]>(`${this.apiUrl}/stockData`, {
      headers: this.getHeaders()
    });
  }
}
