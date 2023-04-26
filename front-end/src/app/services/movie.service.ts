import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import IMovie from '../models/movie.model';
import { environment } from 'src/environments/environment';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Accept': '*/*'
    }),
  }

  constructor(private http: HttpClient,
              private router: Router) { }

  getMovies(crtPage: string): Observable<any> {
    var params = new HttpParams()
    .set('api_key', environment.api_key)
    params = params.append('page', crtPage)
    return this.http.get(`${environment.api_url}/discover/movie/`, {params})
  }

  getMovie(movieID: string): Observable<any> {
    var params = new HttpParams()
    .set('api_key', environment.api_key)
    return this.http.get(`${environment.api_url}/movie/${movieID}`, {params})
  }

  getMovieScore(movieID: string): Observable<any> {
    return this.http.get(environment.backend_url + `/reviews/average/${movieID}`, this.httpOptions)
  }

  searchMovie(searchQuery: string, crtPage: string): Observable<any> {
    var params = new HttpParams()
    .set('api_key', environment.api_key)
    params = params.append('query', searchQuery).append('page', crtPage)
    return this.http.get(`${environment.api_url}/search/movie`, {params})
  }
}
