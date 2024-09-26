import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-purchase-invoice-list',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgForOf,
    DatePipe
  ],
  templateUrl: './purchase-invoice-list.component.html',
  styleUrl: './purchase-invoice-list.component.css'
})
export class PurchaseInvoiceListComponent implements OnInit{

  invoices = [
    {
      id: 1,
      invoiceNo: 'INV001',
      clientVendor: { getClientVendorName: 'Vendor A' },
      date: new Date(),
      price: 1000,
      tax: 100,
      total: 1100,
      invoiceStatus: { value: 'Awaiting Approval' }
    },
    {
      id: 2,
      invoiceNo: 'INV002',
      clientVendor: { getClientVendorName: 'Vendor B' },
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
