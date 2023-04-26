import { Component, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { MovieService } from '../services/movie.service';
import { environment } from 'src/environments/environment';
import IMovie from '../models/movie.model';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class MovieComponent {
  movieId = this.route.snapshot.params['id'];
  movie?: IMovie
  avgScore = 0

  constructor(public route: ActivatedRoute,
              public service: MovieService) {}

  ngOnInit(): void {    
    console.log(this.movieId)
    this.service.getMovie(this.movieId).subscribe(result => {
      console.log(result)
      this.movie = result as IMovie
      if (!this.movie.backdrop_path) {
        this.movie.backdrop_path = environment.generic_wallpaper
      }
      else this.movie.backdrop_path = `${environment.api_image_url}/${this.movie.backdrop_path}`
      this.service.getMovieScore(this.movieId).subscribe(result => {
        this.movie!.user_score = result
      })
      this.movie.vote_average /= 2
    })
  }

}
