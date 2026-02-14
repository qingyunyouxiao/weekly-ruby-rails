import { Component, Injectable } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BackendContent } from './backend-content';
import { HeaderComponent } from './header/header.component';
import { ContentComponent } from './content/content.component';

@Component({
  selector: 'app-root',
  imports: [HeaderComponent,ContentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string = 'angular-app';
  response: string = "empty";
    
  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    this.http.get<BackendContent>('http://localhost:8080/greeting').subscribe(
      data => {
        this.response = data.content;
      }
    );
  }
}

