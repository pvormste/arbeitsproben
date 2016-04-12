using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace PlaneGame
{
	public class Player : DrawableGameComponent
	{
		// Game Reference
		private PlaneGame _plGame; 

		// Players Plane
		public BasePlane Plane { get; set; } 

		public Player (PlaneGame game, BasePlane plane) : base(game)
		{
			// Game Reference
			_plGame = game;

			// The Plane
			Plane = plane;
		}

		public override void Initialize()
		{
			base.Initialize();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);

			// Move Player
			CheckMovement(gameTime);
		}

		public override void Draw(GameTime gameTime)
		{
			base.Draw(gameTime);

			// Draw Players Plane
			Plane.Draw(gameTime);
		}


		/// <summary>
		/// Checks Player Movement
		/// </summary>
		private void CheckMovement(GameTime gameTime)
		{
			float dt = (float)gameTime.ElapsedGameTime.TotalSeconds;

			// Velocity
			Vector2 velocity = Vector2.Zero;

			// GamePad
			GamePadState gamepadState = GamePad.GetState(PlayerIndex.One);

			if (gamepadState.IsConnected)
			{
				// Vertical Stick Direction are inverted by default. So multiply by -1
				float horizontal = gamepadState.ThumbSticks.Left.X * Plane.Speed * dt;
				float vertical = -1 * gamepadState.ThumbSticks.Left.Y * Plane.Speed * dt;

				if(horizontal < 0 && (Plane.Position.X + horizontal > 0))
					velocity += new Vector2(horizontal, 0);
				else if(horizontal > 0 && (Plane.Position.X + Plane.Sprite.Width + horizontal < Game.GraphicsDevice.Viewport.Width))
					velocity += new Vector2(horizontal, 0);

				if(vertical < 0 && (Plane.Position.Y + vertical > 0))
					velocity += new Vector2(0, vertical);
				else if(vertical > 0 && (Plane.Position.Y + Plane.Sprite.Height + vertical <  Game.GraphicsDevice.Viewport.Height))
					velocity += new Vector2(0, vertical);
			}
			else
			{
				// Keyboard Control
				KeyboardState keyboardState = Keyboard.GetState();

				bool up = keyboardState.IsKeyDown(Keys.W) || keyboardState.IsKeyDown(Keys.Up);
				bool down = keyboardState.IsKeyDown(Keys.S) || keyboardState.IsKeyDown(Keys.Down);
				bool left = keyboardState.IsKeyDown(Keys.A) || keyboardState.IsKeyDown(Keys.Left);
				bool right = keyboardState.IsKeyDown(Keys.D) || keyboardState.IsKeyDown(Keys.Right);

				if(left && !right && (Plane.Position.X - (Plane.Speed * dt) > 0))
					velocity += new Vector2(-Plane.Speed * dt, 0);
				else if(right && !left && (Plane.Position.X + Plane.Sprite.Width + (Plane.Speed * dt)  < Game.GraphicsDevice.Viewport.Width))
					velocity += new Vector2(Plane.Speed * dt, 0);

				if(up && !down && (Plane.Position.Y - (Plane.Speed * dt) > 0))
					velocity += new Vector2(0, -Plane.Speed * dt);
				else if(down && !up && (Plane.Position.Y + Plane.Sprite.Height + (Plane.Speed * dt) < Game.GraphicsDevice.Viewport.Height))
					velocity += new Vector2(0, Plane.Speed * dt);
			}

			Plane.Position += velocity;
		}
	}


}

