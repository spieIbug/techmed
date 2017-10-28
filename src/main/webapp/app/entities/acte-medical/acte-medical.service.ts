import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ActeMedical } from './acte-medical.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ActeMedicalService {

    private resourceUrl = SERVER_API_URL + 'api/acte-medicals';

    constructor(private http: Http) { }

    create(acteMedical: ActeMedical): Observable<ActeMedical> {
        const copy = this.convert(acteMedical);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(acteMedical: ActeMedical): Observable<ActeMedical> {
        const copy = this.convert(acteMedical);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ActeMedical> {
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
     * Convert a returned JSON object to ActeMedical.
     */
    private convertItemFromServer(json: any): ActeMedical {
        const entity: ActeMedical = Object.assign(new ActeMedical(), json);
        return entity;
    }

    /**
     * Convert a ActeMedical to a JSON which can be sent to the server.
     */
    private convert(acteMedical: ActeMedical): ActeMedical {
        const copy: ActeMedical = Object.assign({}, acteMedical);
        return copy;
    }
}
