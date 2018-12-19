package me.dara.memoapp.network.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sardor
 */
public final class ApiResponse {
   @Nullable
   private final Object obj;
   @NotNull
   private final Status status;

   @Nullable
   public final Object getObj() {
      return this.obj;
   }

   @NotNull
   public final Status getStatus() {
      return this.status;
   }

   public ApiResponse(@Nullable Object obj, @NotNull Status status) {

      this.obj = obj;
      this.status = status;
   }
}

