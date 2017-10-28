import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Paiement } from './paiement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PaiementService {

    private resourceUrl = SERVER_API_URL + 'api/paiements';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(paiement: Paiement): Observable<Paiement> {
        const copy = this.convert(paiement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(paiement: Paiement): Observable<Paiement> {
        const copy = this.convert(paiement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Paiement> {
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
     * Convert a returned JSON object to Paiement.
     */
    private convertItemFromServer(json: any): Paiement {
        const entity: Paiement = Object.assign(new Paiement(), json);
        entity.dateTransation = this.dateUtils
            .convertDateTimeFromServer(json.dateTransation);
        return entity;
    }

    /**
     * Convert a Paiement to a JSON which can be sent to the server.
     */
    private convert(paiement: Paiement): Paiement {
        const copy: Paiement = Object.assign({}, paiement);

        copy.dateTransation = this.dateUtils.toDate(paiement.dateTransation);
        return copy;
    }
}
