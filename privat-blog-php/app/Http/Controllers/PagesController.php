<?php namespace AboutPV\Http\Controllers;

use AboutPV\Http\Requests;
use AboutPV\Http\Controllers\Controller;

use Illuminate\Http\Request;

class PagesController extends Controller {

	public function home()
	{
		return view('pages.home');
	}

}
