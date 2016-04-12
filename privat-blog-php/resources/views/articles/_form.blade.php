@if($errors->has())
    <div class="alert alert-danger">
        <strong>Error!</strong>
        {{ $errors->first() }}
    </div>
@endif

{!! Form::open(['route' => $route_name]) !!}
    <!-- Title -->
    <div class="form-group">
        {!! Form::label('title', 'Title:') !!}

        @if($article)
            {!! Form::text('title', $article->title, ['class' => 'form-control']) !!}
        @else
            {!! Form::text('title', null, ['class' => 'form-control']) !!}
        @endif
    </div>
    <!-- Textbody -->
    <div class="form-group">
        {!! Form::label('textbody', 'Textbody:') !!}

        @if($article)
            {!! Form::textarea('textbody', $article->textbody, ['class' => 'form-control']) !!}
        @else
            {!! Form::textarea('textbody', null, ['class' => 'form-control']) !!}
        @endif

    </div>

    @if($article)
        {!! Form::hidden('articleid', $article->id) !!}
    @endif
    <!-- Update Article -->
    <div class="form-group">
        {!! Form::submit($buttontext, ['class' => 'btn btn-primary form-control']) !!}
    </div>
{!! Form::close() !!}