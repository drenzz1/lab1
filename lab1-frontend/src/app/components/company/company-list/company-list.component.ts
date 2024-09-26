import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgForOf, NgIf, NgStyle} from "@angular/common";
import {CompanyService} from "../../../services/company.service";
import {CompanyDto} from "../../../common/company";

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgStyle,
    NgForOf
  ],
  templateUrl: './company-list.component.html',
  styleUrl: './company-list.component.css'
})
export class CompanyListComponent implements OnInit{

  companies: CompanyDto[] = [];
  errorMessage: string | null = null;

  constructor(private companyService: CompanyService) {}

  ngOnInit(): void {
    this.loadCompanies();
    console.log(this.companies)
  }

  loadCompanies(): void {
    this.companyService.getAllCompanies().subscribe({
      next: (data) => this.companies = data,
      error: (err) => this.errorMessage = 'Failed to load companies data'
    });
  }

}
