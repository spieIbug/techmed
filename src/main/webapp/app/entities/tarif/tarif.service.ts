import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Tarif } from './tarif.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TarifService {

    private resourceUrl = SERVER_API_URL + 'api/tarifs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(tarif: Tarif): Observable<Tarif> {
        const copy = this.convert(tarif);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tarif: Tarif): Observable<Tarif> {
        const copy = this.convert(tarif);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Tarif> {
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
     * Convert a returned JSON object to Tarif.
     */
    private convertItemFromServer(json: any): Tarif {
        const entity: Tarif = Object.assign(new Tarif(), json);
        entity.dateDebut = this.dateUtils
            .convertDateTimeFromServer(json.dateDebut);
        entity.dateFin = this.dateUtils
            .convertDateTimeFromServer(json.dateFin);
        return entity;
    }

    /**
     * Convert a Tarif to a JSON which can be sent to the server.
     */
    private convert(tarif: Tarif): Tarif {
        const copy: Tarif = Object.assign({}, tarif);

        copy.dateDebut = this.dateUtils.toDate(tarif.dateDebut);

        copy.dateFin = this.dateUtils.toDate(tarif.dateFin);
        return copy;
    }
}
