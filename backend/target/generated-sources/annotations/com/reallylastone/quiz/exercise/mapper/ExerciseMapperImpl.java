package com.reallylastone.quiz.exercise.mapper;

import com.reallylastone.quiz.exercise.model.Exercise;
import com.reallylastone.quiz.exercise.model.ExerciseView;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class ExerciseMapperImpl implements ExerciseMapper {

    @Override
    public ExerciseView mapToView(Exercise exercise) {
        if ( exercise == null ) {
            return null;
        }

        ExerciseView exerciseView = new ExerciseView();

        exerciseView.setQuestion( exercise.getQuestion() );
        exerciseView.setCorrectAnswer( exercise.getCorrectAnswer() );
        exerciseView.setWrongAnswer1( exercise.getWrongAnswer1() );
        exerciseView.setWrongAnswer2( exercise.getWrongAnswer2() );
        exerciseView.setWrongAnswer3( exercise.getWrongAnswer3() );
        exerciseView.setWrongAnswer4( exercise.getWrongAnswer4() );

        return exerciseView;
    }
}
