<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', 'PagesController@home');


Route::group(['prefix' => 'articles'], function ()
{
	Route::get('/', ['as' => 'articles-index', 'uses'  => 'ArticlesController@index']);
	Route::get('edit/{slug}', ['as' => 'articles-edit', 'uses' => 'ArticlesController@edit']);
	Route::get('delete/{slug}', ['as' => 'articles-delete', 'uses' => 'ArticlesController@destroy']);
	Route::get('create', ['as' => 'articles-create', 'uses' => 'ArticlesController@create']);
	Route::post('store', ['as' => 'articles-store', 'uses' => 'ArticlesController@store']);
	Route::post('update', ['as' => 'articles-update', 'uses' => 'ArticlesController@update']);
	Route::get('{slug}', ['as' => 'articles-show', 'uses' => 'ArticlesController@show']);
});


Route::controllers([
	'auth'     => 'Auth\AuthController',
	'password' => 'Auth\PasswordController',
]);
