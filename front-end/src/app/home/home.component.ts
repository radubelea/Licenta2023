import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import IMovie from '../models/movie.model';
import { environment } from 'src/environments/environment';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  trendingMovies: IMovie[] = []

  constructor(private http: HttpClient,
              private movieService: MovieService) {}

  ngOnInit(): void {
    this.movieService.getMovies('1').subscribe(result => {
      this.trendingMovies = result.results.slice(0,3)
      this.trendingMovies.forEach((element: any) => {
        if (!element.backdrop_path) {
          element.backdrop_path = environment.generic_wallpaper
        }
        else element.backdrop_path = `${environment.api_image_url}/${element.backdrop_path}`
        this.movieService.getMovieScore(element.id).subscribe(result => {
          element.user_score = result
        })
        
        element.vote_average /= 2
      })
    })
  }
}
