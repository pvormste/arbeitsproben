using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace PlaneGame
{
	public class Assets
	{
		// Image files
		public static Texture2D ImgTestplane;

		// Fonts
		public static SpriteFont FontTest;

		public static void LoadContent(Game game)
		{
			// Image files
			ImgTestplane = game.Content.Load<Texture2D>("img/Testplane");

			// Fonts
			FontTest = game.Content.Load<SpriteFont>("fonts/TestFont");
		}

		public static void UnloadContent(Game game)
		{
			game.Content.Unload();
		}
	}
}

