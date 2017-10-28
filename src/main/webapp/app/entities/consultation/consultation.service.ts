import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Consultation } from './consultation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConsultationService {

    private resourceUrl = SERVER_API_URL + 'api/consultations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(consultation: Consultation): Observable<Consultation> {
        const copy = this.convert(consultation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(consultation: Consultation): Observable<Consultation> {
        const copy = this.convert(consultation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Consultation> {
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
     * Convert a returned JSON object to Consultation.
     */
    private convertItemFromServer(json: any): Consultation {
        const entity: Consultation = Object.assign(new Consultation(), json);
        entity.dateActe = this.dateUtils
            .convertDateTimeFromServer(json.dateActe);
        return entity;
    }

    /**
     * Convert a Consultation to a JSON which can be sent to the server.
     */
    private convert(consultation: Consultation): Consultation {
        const copy: Consultation = Object.assign({}, consultation);

        copy.dateActe = this.dateUtils.toDate(consultation.dateActe);
        return copy;
    }
}
