<?php

use AboutPV\Article;
use Carbon\Carbon;
use Illuminate\Database\Seeder;
use Illuminate\Database\Eloquent\Model;

class DatabaseSeeder extends Seeder {

	/**
	 * Run the database seeds.
	 *
	 * @return void
	 */
	public function run()
	{
		Model::unguard();

		// $this->call('UserTableSeeder');
		$this->call('ArticleTableSeeder');
		$this->command->info('Article table seeded!');
	}

}

class ArticleTableSeeder extends Seeder {

	public function run()
	{
		DB::table('articles')->delete();

		Article::create([
			'title'        => 'This is the first post!',
			'textbody'     => 'The first post. What a feeling. Can\'t believe it!',
			'published_at' => Carbon::now()
		]);

		Article::create([
			'title'        => 'Wohoo, I did it!',
			'textbody'     => 'It\'s incredible, but I passed this exam. Like a boss!',
			'published_at' => Carbon::now()
		]);
	}
}
