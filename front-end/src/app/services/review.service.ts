import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import IMovie from '../models/movie.model';
import { environment } from 'src/environments/environment';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Accept': '*/*'
    }),
  }

  constructor(private http: HttpClient,
    private router: Router) { }

  getReviews(movieId: string): Observable<any> {
    return this.http.get(environment.backend_url + `/reviews/${movieId}`, this.httpOptions)
  }

  sendReview(text: string, rating: string, movieId: string) : Observable<any> {
    const httpAuthOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Accept': '*/*',
        'Authorization': JSON.parse(localStorage.getItem('user')!).token
      })
    }
    return this.http.post(environment.backend_url + '/review/create', JSON.stringify({ text, rating, movieId }), httpAuthOptions)
  }

  deleteReview(id: string) : Observable<any> {
    return this.http.delete(environment.backend_url + `/review/delete/${id}`, this.httpOptions)
  }
}
