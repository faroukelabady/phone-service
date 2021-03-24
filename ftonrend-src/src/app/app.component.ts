import { Component, ElementRef, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { DataService } from './data.service';
import { Country } from './model/country';
import { Customer } from './model/customer';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'phone-service';
  tableColumns: string[] = ['customerId', 'customerName', 'phone'];
  dataSource: Customer[] = [];
  countries: Country[] = [];
  defaultPageSize = 15;

  pageEvent: PageEvent | undefined;
  pageIndex: number = 0;
  pageSize: number = 15;
  length: number = 0;

  state = ['ALL', 'VALID', 'INVALID'];

  selectedCountry: String = '000';
  selectedState: String = 'ALL';

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.dataService.getCustomers().subscribe((response) => {
      this.dataSource = response.result;
      this.pageIndex = response.pageIndex || 0;
      this.pageSize = response.result.length;
      this.length = response.total;
    });

    this.dataService.getCountries().subscribe((result) => {
      this.countries = result;
    });
  }

  filterByCountry(value: String) {
    this.selectedCountry = value;
    this.getServerData();
  }

  filterByState(value: String) {
    this.selectedState = value;
    this.getServerData();
  }

  public getServerData(event?: PageEvent) {
    if (this.selectedCountry === 'ALL' && this.selectedState === 'ALL') {
      this.dataService.getCustomers(event?.pageIndex, event?.pageSize).subscribe((response) => {
        this.dataSource = response.result;
        this.pageIndex = response.pageIndex || 0;
        this.pageSize = response.result.length;
        this.length = response.total;
      });
    } else if (this.selectedCountry !== 'ALL' && this.selectedState === 'ALL') {
      this.dataService
        .getCustomersByCountryCode(this.selectedCountry, event?.pageIndex, event?.pageSize)
        .subscribe((response) => {
          this.dataSource = response.result;
          this.pageIndex = response.pageIndex || 0;
          this.pageSize = response.result.length;
          this.length = response.total;
        });
    } else if (this.selectedCountry === 'ALL' && this.selectedState !== 'ALL') {
      this.dataService
        .getCustomersByState(this.selectedState, event?.pageIndex, event?.pageSize)
        .subscribe((response) => {
          this.dataSource = response.result;
          this.pageIndex = response.pageIndex || 0;
          this.pageSize = response.result.length;
          this.length = response.total;
        });
    } else if (this.selectedCountry !== 'ALL' && this.selectedState !== 'ALL') {
      this.dataService
        .getCustomersByCountryCodeAndState(
          this.selectedCountry,
          this.selectedState,  event?.pageIndex, event?.pageSize
        )
        .subscribe((response) => {
          this.dataSource = response.result;
          this.pageIndex = response.pageIndex || 0;
          this.pageSize = response.result.length;
          this.length = response.total;
        });
    }

    return event;
  }

}
