package me.dara.memoapp.network.model;



/**
 * @author ulugbek
 */
/**
 * Base class for interpretating remote and local result to UI
 * */
public final class ApiResponse {

   private final Object obj;
   private final Status status;

   public final Object getObj() {
      return this.obj;
   }

   public final Status getStatus() {
      return this.status;
   }

   public ApiResponse( Object obj,  Status status) {

      this.obj = obj;
      this.status = status;
   }
}

