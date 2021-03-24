import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Country } from './model/country';
import { Customer } from './model/customer';
import { ResponseModel } from './model/response.model';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private http: HttpClient) {}

  getCustomers(pageNumber = 0, pageSize = 15): Observable<ResponseModel> {
    return this.http.get<ResponseModel>(`/api/customer`, {
      params: new HttpParams()
        .set('page', `${pageNumber}`)
        .set('size', `${pageSize}`),
    });
  }

  getCustomersByCountryCode(
    countryCode: String,
    pageNumber = 0,
    pageSize = 15
  ): Observable<ResponseModel> {
    return this.http.get<ResponseModel>(`/api/customer/country/${countryCode}`, {
      params: new HttpParams()
        .set('page', `${pageNumber}`)
        .set('size', `${pageSize}`),
    });
  }

  getCustomersByState(
    state: String,
    pageNumber = 0,
    pageSize = 15
  ): Observable<ResponseModel> {
    return this.http.get<ResponseModel>(`/api/customer/state/${state}`, {
      params: new HttpParams()
        .set('page', `${pageNumber}`)
        .set('size', `${pageSize}`),
    });
  }

  getCustomersByCountryCodeAndState(
    countryCode: String,
    state: String,
    pageNumber = 0,
    pageSize = 15
  ): Observable<ResponseModel> {
    return this.http.get<ResponseModel>(
      `/api/customer/country/${countryCode}/state/${state}`, {
        params: new HttpParams()
          .set('page', `${pageNumber}`)
          .set('size', `${pageSize}`),
      }
    );
  }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(`/api/country`);
  }
}
