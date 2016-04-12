<?php namespace AboutPV\Http\Controllers;

use AboutPV\Article;
use AboutPV\Http\Requests;
use AboutPV\Http\Controllers\Controller;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;

class ArticlesController extends Controller {

	/**
	 * Display a listing of the resource.
	 *
	 * @return Response
	 */
	public function index()
	{
		$articles = Article::all();

		return view('articles.index', compact('articles'));
	}

	/**
	 * Show the form for creating a new resource.
	 *
	 * @return Response
	 */
	public function create()
	{
		return view('articles.create');
	}

	/**
	 * Store a newly created resource in storage.
	 *
	 * @return Response
	 */
	public function store(Request $request)
	{
		$input = $this->getValidatedFormInput($request, true);

		// Validation failed
		if(gettype($input) == 'object')
			return $input;

		Article::create($input);
		return redirect()->route('articles-index');
	}

	/**
	 * Display the specified resource.
	 *
	 * @param  string  $slug
	 * @return Response
	 */
	public function show($slug)
	{
		$article = $this->findArticleBySlug($slug);

		return view('articles.show', compact('article'));
	}

	/**
	 * Show the form for editing the specified resource.
	 *
	 * @param  string  $slug
	 * @return Response
	 */
	public function edit($slug)
	{
		$article = $this->findArticleBySlug($slug);

		return view('articles.edit', compact('article'));
	}

	/**
	 * Update the specified resource in storage.
	 *
	 * @param  Request  $request
	 * @return Response
	 */
	public function update(Request $request)
	{
		$input = $this->getValidatedFormInput($request, false);

		// Validation failed
		if(gettype($input) == 'object')
			return $input;

		$article = Article::find($input['articleid']);
		$article->title = $input['title'];
		$article->textbody = $input['textbody'];

		$article->save();

		return redirect()->route('articles-index');
	}

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function destroy($slug)
	{
		$article = $this->findArticleBySlug($slug);

		$article->delete();

		return redirect()->route('articles-index');
	}

	// -------------------------------

	private function findArticleBySlug($slug)
	{
		$article = Article::findBySlug($slug);
		if($article == null)
			abort(404);

		return  $article;
	}

	private function validateArticleInput($request)
	{
		return Validator::make($request->all(), [
			'title' => 'required|min:3',
			'textbody' => 'required|min:3'
		]);
	}

	private function getValidatedFormInput($request, $withPublishDate)
	{
		$v = $this->validateArticleInput($request);

		if($v->fails())
		{
			return redirect()->back()->withErrors($v->errors());
		}

		$input = $request->all();
		if($withPublishDate)
			$input['published_at'] = Carbon::now();

		return $input;
	}

}
