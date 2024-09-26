import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Invoice} from "../common/invoice";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api/dashboard'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get the last 3 invoices
  getLast3Invoices(): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.apiUrl}/last3invoices`, {
      headers: this.getHeaders()
    });
  }
}
