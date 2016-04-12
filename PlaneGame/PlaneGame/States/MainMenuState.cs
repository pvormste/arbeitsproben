using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;

namespace PlaneGame
{
	public class MainMenuState : BaseState
	{
		public MainMenuState (PlaneGame game) : base(game)
		{

		}

		public override void Initialize()
		{
			base.Initialize();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);

			CheckInput();
		}

		public override void Draw (GameTime gameTime)
		{
			Game.GraphicsDevice.Clear(Color.Black);


			plGame.SpriteBatch.Begin();
			base.Draw (gameTime);

			plGame.SpriteBatch.DrawString(Assets.FontTest, "P L A N E G A M E", new Vector2(Game.GraphicsDevice.Viewport.Width / 2 - 115, 120), Color.White);
			plGame.SpriteBatch.DrawString(Assets.FontTest, "Press Enter", new Vector2(Game.GraphicsDevice.Viewport.Width / 2 - 65, 150), Color.White);
			plGame.SpriteBatch.End();
		}

		private void CheckInput()
		{
			KeyboardState keyboardState = Keyboard.GetState();

			bool enterPressed = keyboardState.IsKeyDown(Keys.Enter);

			if(enterPressed)
				plGame.StateManager.ChangeState(new DebugState(plGame));
		}
	}
}

