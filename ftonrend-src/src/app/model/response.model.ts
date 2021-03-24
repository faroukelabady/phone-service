import { Customer } from "./customer";

export interface ResponseModel {
  total: number;
  pageIndex: number;
  pageNumbers: number;
  result: Customer[];
}
