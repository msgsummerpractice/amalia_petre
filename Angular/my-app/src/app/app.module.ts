import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {App} from './app';
import {DogService} from './Dog/dog.service';
import {DogsComponent} from './Dog/dog.component';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [App, DogsComponent],
  imports: [BrowserModule, HttpClientModule],
  providers: [DogService]
})
export class AppModule {}