import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgForOf, NgIf, NgStyle} from "@angular/common";
import {ClientVendorDto} from "../../../common/client-vendor-dto";
import {ClientVendorService} from "../../../services/client-vendor.service";

@Component({
  selector: 'app-client-vendor-list',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgForOf,
    NgStyle
  ],
  templateUrl: './client-vendor-list.component.html',
  styleUrl: './client-vendor-list.component.css'
})
export class ClientVendorListComponent implements OnInit{
  clientVendors: ClientVendorDto[] = [];
  errorMessage: string | null = null;

  constructor(private clientVendorService: ClientVendorService) {}

  ngOnInit(): void {
    this.loadClientVendors();
  }

  loadClientVendors(): void {
    this.clientVendorService.getAllClientVendors().subscribe({
      next: (data) => this.clientVendors = data,
      error: (err) => this.errorMessage = 'Failed to load client/vendor data'
    });
  }

}
