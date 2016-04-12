<?php namespace AboutPV;

use Cviebrock\EloquentSluggable\SluggableInterface;
use Cviebrock\EloquentSluggable\SluggableTrait;
use Illuminate\Database\Eloquent\Model;

class Article extends Model implements SluggableInterface {

	use SluggableTrait;

	protected $sluggable = array(
		'build_from' => 'title',
		'save_to'    => 'slug',
		'on_update'	=> 'true'
	);

	protected $fillable = ['title', 'textbody', 'published_at'];

}
