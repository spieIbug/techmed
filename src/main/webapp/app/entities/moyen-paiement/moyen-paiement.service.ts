import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MoyenPaiement } from './moyen-paiement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MoyenPaiementService {

    private resourceUrl = SERVER_API_URL + 'api/moyen-paiements';

    constructor(private http: Http) { }

    create(moyenPaiement: MoyenPaiement): Observable<MoyenPaiement> {
        const copy = this.convert(moyenPaiement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(moyenPaiement: MoyenPaiement): Observable<MoyenPaiement> {
        const copy = this.convert(moyenPaiement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MoyenPaiement> {
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
     * Convert a returned JSON object to MoyenPaiement.
     */
    private convertItemFromServer(json: any): MoyenPaiement {
        const entity: MoyenPaiement = Object.assign(new MoyenPaiement(), json);
        return entity;
    }

    /**
     * Convert a MoyenPaiement to a JSON which can be sent to the server.
     */
    private convert(moyenPaiement: MoyenPaiement): MoyenPaiement {
        const copy: MoyenPaiement = Object.assign({}, moyenPaiement);
        return copy;
    }
}
