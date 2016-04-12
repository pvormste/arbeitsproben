using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;

namespace PlaneGame
{
	public class StateManager : GameComponent
	{
		// EventHandler
		public event EventHandler OnStateChange;

		// State Stack
		private Stack<BaseState> _stateStack = new Stack<BaseState>();

		// DrawOrder
		private const int _startDrawOrder = 5000;
		private const int _deltaDrawOrder = 100;
		private int _drawOrder;


		public StateManager(Game game) : base(game)
		{
			_drawOrder = _startDrawOrder;
		}

		public override void Initialize ()
		{
			base.Initialize ();
		}

		public override void Update(GameTime gameTime)
		{
			base.Update(gameTime);
		}


		/// <summary>
		/// Returns the current state
		/// </summary>
		public BaseState CurrentState()
		{
			return _stateStack.Peek();
		}

		/// <summary>
		/// Pushs a new State in the State Stack.
		/// Increases the Draw Order, so that the new state
		/// will be drawn on top
		/// </summary>
		public void PushState(BaseState state)
		{
			_drawOrder += _deltaDrawOrder;
			state.DrawOrder = _drawOrder;

			AddState(state);

			if(OnStateChange != null)
				OnStateChange(this, null);
		}

		/// <summary>
		/// Pops the State, which is on top. (Removes it in other words)
		/// Decreases the draw order.
		/// </summary>
		public void PopState()
		{
			if(_stateStack.Count > 0)
			{
				_drawOrder -= _deltaDrawOrder;

				RemoveState();
				if(OnStateChange != null)
					OnStateChange(this, null);
			}
		}

		/// <summary>
		/// Changes the State bei removing all others in the stack.
		/// Sets the draw order to the start value
		/// </summary>
		public void ChangeState(BaseState state)
		{
			while(_stateStack.Count > 0)
				RemoveState();

			_drawOrder = _startDrawOrder;

			PushState(state);
		}


		/// <summary>
		/// Adds a state in the Stack and to the Game instance
		/// </summary>
		private void AddState(BaseState state)
		{
			_stateStack.Push(state);
			Game.Components.Add(state);
			OnStateChange += state.StateChange;
		}

		/// <summary>
		/// Removes the State on top of the stack and from the
		/// game instance.
		/// </summary>
		private void RemoveState()
		{
			BaseState state = _stateStack.Pop();
			Game.Components.Remove(state);
			OnStateChange -= state.StateChange;
		}
	}
}

