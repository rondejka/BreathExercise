from rest_framework.serializers import ModelSerializer

from . import models


class ScoreSerializer(ModelSerializer):
    class Meta:
        model = models.Score
        fields = [
            "time",
            "time1",
            "time2",
            "time3",
            "time4",
            "time_max",
            "time_avg",
            "created_at",
            "updated_at",
        ]
