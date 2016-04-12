using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace PlaneGame
{
	public class Hitbox : DrawableGameComponent
	{
		// Game Reference
		private PlaneGame _plGame;

		// Reference to the component
		private Base2D _component;

		// Hitbox Rectangle
		public Rectangle Rectangle { get; set; }

		public Hitbox (PlaneGame game, Base2D component, int width, int height, int offsetX, int offsetY) : base(game)
		{
			// Game Reference
			_plGame = game;

			// Component
			_component = component;

			// Create Rectangle
			Rectangle = new Rectangle(offsetX, offsetY, width, height);

			// Show the Rectangle?
			Visible = true;
		}

		public override void Initialize()
		{
			base.Initialize();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);
		}

		public override void Draw(GameTime gameTime)
		{
			base.Draw(gameTime);

			#if DEBUG
			// Draw Lines of the Hitbox
			if(Visible)
			{
				Texture2D rect = new Texture2D(Game.GraphicsDevice, Rectangle.Width, Rectangle.Height);
				Color[] data = new Color[Rectangle.Width * Rectangle.Height];

				for (int i = 0; i < data.Length; ++i)
				{
					if( i < Rectangle.Width || (i % Rectangle.Width) == 0 || (i+1) % Rectangle.Width == 0 || i > data.Length - Rectangle.Width)
						data[i] = Color.Red;
				}

				rect.SetData<Color>(data);

				_plGame.SpriteBatch.Draw(rect, new Vector2(Rectangle.X + _component.Position.X, Rectangle.Y + _component.Position.Y), Color.White);
			}
			#endif
		}
	}
}

