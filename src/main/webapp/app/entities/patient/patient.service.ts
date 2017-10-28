import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Patient } from './patient.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PatientService {

    private resourceUrl = SERVER_API_URL + 'api/patients';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(patient: Patient): Observable<Patient> {
        const copy = this.convert(patient);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(patient: Patient): Observable<Patient> {
        const copy = this.convert(patient);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Patient> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Patient.
     */
    private convertItemFromServer(json: any): Patient {
        const entity: Patient = Object.assign(new Patient(), json);
        entity.dateNaissance = this.dateUtils
            .convertDateTimeFromServer(json.dateNaissance);
        return entity;
    }

    /**
     * Convert a Patient to a JSON which can be sent to the server.
     */
    private convert(patient: Patient): Patient {
        const copy: Patient = Object.assign({}, patient);

        copy.dateNaissance = this.dateUtils.toDate(patient.dateNaissance);
        return copy;
    }
}
