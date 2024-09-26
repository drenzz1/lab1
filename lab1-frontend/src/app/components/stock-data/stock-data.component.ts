import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-stock-data',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './stock-data.component.html',
  styleUrl: './stock-data.component.css'
})
export class StockDataComponent implements OnInit{

  invoiceProducts = [
    {
      invoice: { invoiceNo: 'INV001', date: new Date() },
      quantity: 10,
      product: { name: 'Product A', category: { description: 'Category X' } }
    },
    {
      invoice: { invoiceNo: 'INV002', date: new Date() },
      quantity: 5,
      product: { name: 'Product B', category: { description: 'Category Y' } }
    }
    // Add more objects as needed
  ];

  ngOnInit(): void {
    // Initialization logic if needed
  }

}
