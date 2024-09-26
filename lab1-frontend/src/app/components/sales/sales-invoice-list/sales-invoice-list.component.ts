import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-sales-invoice-list',
  standalone: true,
  imports: [
    RouterLink,
    DatePipe,
    NgForOf,
    NgIf
  ],
  templateUrl: './sales-invoice-list.component.html',
  styleUrl: './sales-invoice-list.component.css'
})
export class SalesInvoiceListComponent  implements OnInit{

  invoices = [
    // Example invoice data
    {
      id: 1,
      invoiceNo: 'INV123',
      clientVendor: { getClientVendorName: 'Client A' },
      date: new Date(),
      price: 2000,
      tax: 200,
      total: 2200,
      invoiceStatus: { value: 'Awaiting Approval' }
    },
    {
      id: 2,
      invoiceNo: 'INV124',
      clientVendor: { getClientVendorName: 'Client B' },
      date: new Date(),
      price: 1500,
      tax: 150,
      total: 1650,
      invoiceStatus: { value: 'Approved' }
    }
    // Add more invoices as needed
  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
    // Initialization logic if needed
  }

}
