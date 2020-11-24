import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Locker } from '../model/locker'
import { ReleasedLuggage } from '../model/releasedLuggage';



@Injectable({ providedIn: 'root' })
export class StowService {

    private API = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    numberTotalOfLockers(): Observable<any> {
        return this.http.get<number>(`${this.API}/storageCapacity`);
    }

    numberOfAvailableLockers(): Observable<any> {
        return this.http.get<number>(`${this.API}/occupancy`);
    }

    dropOff(): Observable<Locker> {
        return this.http.post<Locker>(`${this.API}/saveLuggage`, "");
    }

    submitCode(code$: string): Observable<ReleasedLuggage> {
        return this.http.post<ReleasedLuggage>(`${this.API}/pickup?code=${code$}`, "");
    }

    downloadFile(id$: number): Observable<any> {
        return this.http.get(`${this.API}/luggage/download?id=${id$}`, { responseType: 'blob' });
    }

    releaseLuggage(code$: string) {
        return this.http.post(`${this.API}/release?code=${code$}`, "");
    }
}
