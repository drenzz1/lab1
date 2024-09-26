import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-profit-loss',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './profit-loss.component.html',
  styleUrl: './profit-loss.component.css'
})
export class ProfitLossComponent {

  monthlyProfitLossDataMap:any = {
    'January': 5000,
    'February': -2000,
    // Add other months and their data here
  };

  monthlyProfitLossArray: { key: string, value: number }[] = [];

  ngOnInit(): void {
    this.monthlyProfitLossArray = Object.keys(this.monthlyProfitLossDataMap).map(key => ({
      key: key,
      value: this.monthlyProfitLossDataMap[key]
    }));
  }

}
