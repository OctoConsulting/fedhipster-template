import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMovie, Movie } from 'app/shared/model/movie/movie.model';
import { MovieService } from './movie.service';

@Component({
  selector: 'jhi-movie-update',
  templateUrl: './movie-update.component.html'
})
export class MovieUpdateComponent implements OnInit {
  movie: IMovie;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    average: []
  });

  constructor(protected movieService: MovieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movie }) => {
      this.updateForm(movie);
      this.movie = movie;
    });
  }

  updateForm(movie: IMovie) {
    this.editForm.patchValue({
      id: movie.id,
      name: movie.name,
      average: movie.average
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const movie = this.createFromForm();
    if (movie.id !== undefined) {
      this.subscribeToSaveResponse(this.movieService.update(movie));
    } else {
      this.subscribeToSaveResponse(this.movieService.create(movie));
    }
  }

  private createFromForm(): IMovie {
    const entity = {
      ...new Movie(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      average: this.editForm.get(['average']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovie>>) {
    result.subscribe((res: HttpResponse<IMovie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
